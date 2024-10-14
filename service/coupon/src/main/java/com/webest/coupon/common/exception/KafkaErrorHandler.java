package com.webest.coupon.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaErrorHandler implements CommonErrorHandler {

    // 컨슈머에서 에러 발생시 처리를 한다.
    @Override
    public boolean handleOne(
        Exception thrownException,
        ConsumerRecord<?, ?> record,
        Consumer<?, ?> consumer,
        MessageListenerContainer container) {
        Throwable cause = thrownException.getCause();
        if (cause instanceof CouponException) {
            // 예상 가능한 오류이므로 에러를 남기지 않는다.
            log.error("Known Error: {}, {}", record.value(),
                cause.getMessage());
        } else {
            log.error("Error log in kafka listener message: {}, {}", record.value(),
                cause.getMessage());
        }

        return true;
    }

}
