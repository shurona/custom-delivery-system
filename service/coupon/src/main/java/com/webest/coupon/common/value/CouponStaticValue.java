package com.webest.coupon.common.value;

public final class CouponStaticValue {

    /**
     * 쿠폰 발급 그룹 아이디
     */
    public static final String KAFKA_COUPON_ISSUE_GROUP_ID = "coupon.issue.processor.group";

    /**
     * 쿠폰 발급 토픽 아이디
     */
    public static final String KAFKA_COUPON_ISSUE_TOPIC_ID = "coupon.issuance";

    /**
     * 쿠폰 발급 파티션 갯수
     */
    public static final String COUPON_PARTITION_NUMBER = "3";

    /**
     * 쿠폰 비율 할인 최대 값
     */
    public static Integer PERCENTAGE_MAX_VALUE = 90;

    /**
     * 쿠폰 레디스 상태 정보
     */
    public static Integer COUPON_REDIS_IS_OPEN = 0;
    public static Integer COUPON_REDIS_IS_OUT_OF_STOCK = 1;
    public static Integer COUPON_REDIS_IS_CLOSE = 2;


    private CouponStaticValue() {
        // prevent init
    }
}
