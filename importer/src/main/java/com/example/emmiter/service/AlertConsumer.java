package com.example.emmiter.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.example.emmiter.repository.EmitterRepository;
import com.example.emmiter.service.dto.EmployeeAlertDTO;

@Service
public class AlertConsumer {
	
    private final Logger log = LoggerFactory.getLogger(AlertConsumer.class);
    
    private final AtomicBoolean closed = new AtomicBoolean(false);
    public static final String TOPIC = "topic_alert";
    private final KafkaProperties kafkaProperties;
    private KafkaConsumer<String, String> kafkaConsumer;

    private EmitterRepository storeAlertRepository;
    private EmailService emailService;


    private ExecutorService executorService = Executors.newCachedThreadPool();

    public AlertConsumer(KafkaProperties kafkaProperties, EmitterRepository storeAlertRepository, EmailService emailService) {

        this.kafkaProperties = kafkaProperties;

        this.storeAlertRepository = storeAlertRepository;

        this.emailService = emailService;
    }
    
    @PostConstruct
        public void start() {
            log.info("Kafka consumer starting...");
            this.kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
            kafkaConsumer.subscribe(Collections.singletonList(TOPIC));
            log.info("Kafka consumer started");
            executorService.execute(() -> {
                try {
                    while (!closed.get()) {
                        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                        for (ConsumerRecord<String, String> record : records) {
                            log.info("Consumed message in {} : {}", TOPIC, record.value());
                            ObjectMapper objectMapper = new ObjectMapper();
    
                            EmployeeAlertDTO employeeAlertDTO = objectMapper.readValue(record.value(), EmployeeAlertDTO.class);
                            emailService.sendSimpleMessage(employeeAlertDTO);
    
                        }
    
                    }
    
                    kafkaConsumer.commitSync();
    
                } catch (WakeupException e) {

                    // Ignore exception if closing
                    if (!closed.get()) throw e;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {

                    log.info("Kafka consumer close");
                    kafkaConsumer.close();

                }

            });
        }

        public KafkaConsumer<String, String> getKafkaConsumer() {
            return kafkaConsumer;
        }
        
        public void shutdown() {
            log.info("Shutdown Kafka consumer");
            closed.set(true);
            kafkaConsumer.wakeup();

        }
    }
}
