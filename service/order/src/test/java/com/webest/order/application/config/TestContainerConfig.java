package com.webest.order.application.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfig implements BeforeAllCallback {

    public static MongoDBContainer mongoDBContainer;
    public static KafkaContainer kafkaContainer;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        startMongoDbContainer();
        startKafkaContainers();
    }

    private void startMongoDbContainer() {
        mongoDBContainer = new MongoDBContainer("mongo:4.0.10");
        mongoDBContainer.start();

        // Mongo db 컨테이터 세팅
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getConnectionString());
        System.setProperty("spring.data.mongodb.database", "testdb");

    }

    private void startKafkaContainers() {

        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));
        kafkaContainer.start();

        System.out.println("야후 : " + kafkaContainer.getBootstrapServers());

        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
    }
}
