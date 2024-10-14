package com.webest.order.infrastructure.configuration;

import com.webest.order.infrastructure.messaging.topic.OrderTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // 파티션이 3개인 토픽 생성
    @Bean
    public NewTopic myTopic() {
        return TopicBuilder.name(OrderTopic.CREATED.getTopic())
                .partitions(1)
                .build();
    }
}
