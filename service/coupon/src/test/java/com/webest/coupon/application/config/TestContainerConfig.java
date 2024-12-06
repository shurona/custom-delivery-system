package com.webest.coupon.application.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfig implements BeforeAllCallback {

    public static final String REDIS_IMAGE = "redis:7.4.0-alpine";
    public static final String KAFKA_IMAGE = "confluentinc/cp-kafka:7.7.1.arm64";

    public static final Integer port = 6379;
    public static final Integer kafkaPort = 9092;

    public static GenericContainer<?> redis;
    public static GenericContainer<?> kafka;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // redis
        startRedisContainer();

        // kafka
//        startKafkaContainer();
    }

    private void startRedisContainer() {
        redis = new GenericContainer(
            DockerImageName.parse(REDIS_IMAGE)).withExposedPorts(port).withReuse(true);

        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", String.valueOf(redis.getMappedPort(port)));

    }

    private void startKafkaContainer() {
        kafka = new GenericContainer(
            DockerImageName.parse(KAFKA_IMAGE)).withExposedPorts(kafkaPort).withReuse(true);

        kafka.start();
        System.setProperty("spring.data.kafka.host", kafka.getHost());
        System.setProperty("spring.data.kafka.port",
            String.valueOf(kafka.getMappedPort(kafkaPort)));
    }


}
