package service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.EmployeeAlert;
import model.EmployeeAlertDto;
import model.EmployeeAlertRepository;
import util.KafkaProperties;

@Service
public class ConsumerService {
	
    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public static final String TOPIC = "topic_alert";

    private final KafkaProperties kafkaProperties;

    private KafkaConsumer<String, String> kafkaConsumer;

    private EmployeeAlertRepository alertRepository;

    private EmailService emailService;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ConsumerService(KafkaProperties kafkaProperties, EmployeeAlertRepository alertRepository, EmailService emailService) {
        this.kafkaProperties = kafkaProperties;
        this.alertRepository = alertRepository;
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
                        EmployeeAlertDto employeeAlertDto = objectMapper.readValue(record.value(), EmployeeAlertDto.class);
                        EmployeeAlert employeeAlert = new EmployeeAlert();
                        employeeAlert.setEmployeeName(employeeAlertDto.getEmployee_name());
                        employeeAlert.setEmployeeStatus(employeeAlertDto.getEmplyee_satus());
                        employeeAlert.setTimestamp(Instant.now());
                        alertRepository.save(employeeAlert);

                        emailService.sendSimpleMessage(employeeAlertDto);

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
