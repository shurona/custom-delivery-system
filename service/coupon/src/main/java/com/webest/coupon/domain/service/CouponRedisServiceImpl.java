package com.webest.coupon.domain.service;

import static com.webest.coupon.common.value.CouponStaticValue.COUPON_REDIS_STATUS_KEY;
import static com.webest.coupon.common.value.CouponStaticValue.COUPON_REDIS_WAITING_KEY;

import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponRedisServiceImpl implements CouponRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Integer> redisIntegerTemplate;

    /**
     * zSet에서 데이터 추가
     */
    @Override
    public void addCouponToQueue(Long couponId, Long userId) {

        String redisKey = convertCouponToRedisKeyForWaiting(couponId);

        redisTemplate.opsForZSet().add(redisKey, userId, System.currentTimeMillis());

    }

    /**
     * zSet에서 데이터 삭제
     */
    @Override
    public void popCouponFromQueue(Long couponId, Long userId) {
        String redisKey = convertCouponToRedisKeyForWaiting(couponId);

        redisTemplate.opsForZSet().remove(redisKey, userId);
    }

    /**
     * zSet에서 현재 위치 조회
     */
    @Override
    public Long checkCurrentOffset(Long couponId, Long userId) {
        String redisKey = convertCouponToRedisKeyForWaiting(couponId);

        return redisTemplate.opsForZSet().rank(redisKey, userId);
    }

    /**
     * 쿠폰의 상태 조정
     */
    @Override
    public void changeCouponOpenStatus(Long couponId, Integer wishStatus, long durationHour) {
        String redisKey = convertCouponIsOpenRedisKey(couponId);

        if (durationHour <= 0) {
            throw new CouponException(CouponErrorCode.INVALID_INPUT);
        }

        ValueOperations<String, Integer> opsForValue =
            redisIntegerTemplate.opsForValue();

        // 상태 지정
        opsForValue.set(redisKey, wishStatus, durationHour, TimeUnit.HOURS);
    }

    /**
     * 현재 쿠폰의 상태 조회
     */
    @Override
    public Integer checkCouponStatus(Long couponId) {
        String redisKey = convertCouponIsOpenRedisKey(couponId);

        ValueOperations<String, Integer> opsForValue =
            redisIntegerTemplate.opsForValue();

        return opsForValue.get(redisKey);
    }

    /* ===========================================================================================
        private method
     ===========================================================================================*/


    /**
     * 대기열을 위한 couponId를 redisKey로 변환해준다.
     */
    private String convertCouponToRedisKeyForWaiting(Long couponId) {
        return COUPON_REDIS_WAITING_KEY + couponId;
    }

    /**
     * 현재 Coupon 발급 상태를 위한 키를 지정한다.
     */
    private String convertCouponIsOpenRedisKey(Long couponId) {
        return COUPON_REDIS_STATUS_KEY + couponId;
    }
}
