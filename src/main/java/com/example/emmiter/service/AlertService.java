package com.example.emmiter.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.example.emmiter.repository.Employee;
import com.example.emmiter.service.dto.EmployeeAlertDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlertService {
	
	private final Logger log = LoggerFactory.getLogger(AlertService.class);

	    private static final String TOPIC = "topic_alert";

	    private final KafkaProperties kafkaProperties;

	    private final static Logger logger = LoggerFactory.getLogger(AlertService.class);

	    private KafkaProducer<String, String> producer;

	    private final ObjectMapper objectMapper = new ObjectMapper();

	    public AlertService(KafkaProperties kafkaProperties) {
	    this.kafkaProperties = kafkaProperties;
	    }

	    @PostConstruct
	        public void initialize(){
	    
	            log.info("Kafka producer initializing...");
	            this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
	            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	            log.info("Kafka producer initialized");
	        }
	    
	    public void alertStoreStatus(Employee employee) {
	    	        try {
	    	            EmployeeAlertDTO employeeAlertDTO = new EmployeeAlertDTO(employee);
	    	            String message = objectMapper.writeValueAsString(storeAlertDTO);
	    	            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
	    	            producer.send(record);
	    	        } catch (JsonProcessingException e) {
	    	            logger.error("Could not send store alert", e);
	    	            throw new AlertServiceException(e);
	    	        }
	    	    }
	    
	    @PreDestroy
	        public void shutdown() {
	            log.info("Shutdown Kafka producer");
	            producer.close();
	        }
}
