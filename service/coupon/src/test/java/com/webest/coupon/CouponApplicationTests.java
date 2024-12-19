package com.webest.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

//@ExtendWith(TestContainerConfig.class)
//@SpringBootTest
//@Testcontainers
class CouponApplicationTests {

    private static final String KAFKA_IMAGE = "confluentinc/cp-kafka:7.7.1";

    //kafka Container
    @Container
    static final KafkaContainer kafka = new KafkaContainer(
        DockerImageName.parse(KAFKA_IMAGE));

    @DynamicPropertySource
    static void setKafkaProperties(DynamicPropertyRegistry registry) {
//        System.out.println("?? 여기는 실행되나요? " + kafka.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }


    @Test
    void contextLoads() {
    }

}
