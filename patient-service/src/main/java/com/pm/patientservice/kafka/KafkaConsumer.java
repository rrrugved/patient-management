package com.pm.patientservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    // Using the manual logger we discussed earlier
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    // This listens to the "patient" topic
    @KafkaListener(topics = "patient", groupId = "local-debug-group")
    public void consumeMessage(byte[] messageBytes) {
        try {
            // 1. Take the raw bytes from Kafka and parse them back into the Protobuf object
            PatientEvent patientEvent = PatientEvent.parseFrom(messageBytes);

            // 2. Print out the readable data to your IDE console!
            log.info("✅ SUCCESS! Consumed Patient Event from Kafka:");
            log.info("   Patient ID : {}", patientEvent.getPatientId());
            log.info("   Name       : {}", patientEvent.getName());
            log.info("   Email      : {}", patientEvent.getEmail());
            log.info("   Event Type : {}", patientEvent.getEventType());
            log.info("------------------------------------------------");

        } catch (Exception e) {
            log.error("❌ Failed to parse incoming Protobuf message", e);
        }
    }
}