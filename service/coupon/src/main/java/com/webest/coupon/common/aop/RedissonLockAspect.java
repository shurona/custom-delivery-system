package com.webest.coupon.common.aop;

import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.common.parser.CustomSpringELParser;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class RedissonLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(com.webest.coupon.common.aop.RedissonLock)")
    public boolean redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock lockAnnotation = method.getAnnotation(RedissonLock.class);

        String lockKey =
            method.getName() + "-" + CustomSpringELParser.getDynamicValue(
                signature.getParameterNames(),
                joinPoint.getArgs(), lockAnnotation.value());

        // 순서의 보장을 최대한 맞추기 위해 FairLock 사용
        RLock lock = redissonClient.getFairLock(lockKey);
        boolean output;

        try {
            boolean lockable = lock.tryLock(
                lockAnnotation.waitTime(), lockAnnotation.leaseTime(), TimeUnit.MILLISECONDS);

            if (!lockable) {
                // 대기 시간 초과
                log.info("Lock 획득 실패 : {}", lockKey);
                throw new CouponException(CouponErrorCode.LOCK_WAITING_TIMEOUT);
            }

            output = (boolean) joinPoint.proceed();

        } catch (InterruptedException e) {
            log.error("락 얻는 과정에서 에러 발생", e);
            throw e;
        } finally {
            // 락이 잠겨 있고 현재 쓰레드에 락이 걸려있는지 확인한다.
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                // 락 해제
                lock.unlock();
            }
        }
        return output;
    }

}
