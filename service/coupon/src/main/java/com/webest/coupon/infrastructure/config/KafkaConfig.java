package com.webest.coupon.infrastructure.config;

import static com.webest.coupon.common.value.CouponStaticValue.COUPON_PARTITION_NUMBER;
import static com.webest.coupon.common.value.CouponStaticValue.KAFKA_COUPON_ISSUE_TOPIC_ID;

import com.webest.coupon.common.exception.KafkaErrorHandler;
import com.webest.coupon.infrastructure.kafka.KafkaProducerListener;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@RequiredArgsConstructor
@EnableKafka
@Configuration
public class KafkaConfig {

    private final KafkaErrorHandler kafkaErrorHandler;
    private final KafkaProducerListener kafkaProducerListener;

    @Value("${spring.kafka.bootstrap-servers}")
    private String serverUrl;


    /**
     * Product 설정
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        HashMap<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Kafka Template
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> stringKafkaTemplate = new KafkaTemplate<>(
            producerFactory());

        stringKafkaTemplate.setProducerListener(kafkaProducerListener);

        return stringKafkaTemplate;
    }

    /**
     * Consume 설정
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        HashMap<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * 카프카 리스너 설정
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }

    /**
     * 특정 토픽의 파티션의 크기를 지정하기 위함
     */
    @Bean
    public NewTopic myTopic() {
        return TopicBuilder.name(KAFKA_COUPON_ISSUE_TOPIC_ID)
            .partitions(Integer.parseInt(COUPON_PARTITION_NUMBER))
            .replicas(1)
            .build();
    }

}
