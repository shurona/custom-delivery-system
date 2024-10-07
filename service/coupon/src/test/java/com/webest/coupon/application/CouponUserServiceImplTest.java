package com.webest.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.webest.coupon.application.config.TestContainerConfig;
import com.webest.coupon.domain.model.DateType;
import com.webest.coupon.domain.model.DiscountType;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(TestContainerConfig.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CouponUserServiceImplTest {

    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private CouponUserServiceImpl couponUserService;


    @Nested
    @DisplayName("쿠폰 동시성 테스트")
    class couponConcurrent {


        private Long couponId;

        @BeforeEach
        void initBefore() {
            // User과 연관된 테이블은 현재 미존재
            Long couponId = couponService.createCoupon(new CouponCreateRequestDto(
                "치킨 할인 쿠폰",
                1,
                DateType.DAY,
                LocalDate.parse("2024-10-01"),
                LocalDate.parse("2024-10-10"),
                DiscountType.FIXED,
                3000,
                200
            ));
            this.couponId = couponId;
        }

        @DisplayName("동시에 100개 요청")
        @Test
        public void 동시에_100개_요청() throws InterruptedException {
            // given

            CouponResponseDto couponById1 = couponService.findCouponById(couponId);

            final int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(30);
            CountDownLatch latch = new CountDownLatch(threadCount);
            // when
            final Long currentMemberId = 1L;
            for (int i = 0; i < threadCount; i++) {
                Long ad = i + 10L;
                executorService.submit(() -> {
                    try {
                        couponUserService.issueCouponToUser(couponId, ad);
                    } catch (Exception e) {
//                        System.out.println("에러 발생 : " + ad + " : " + e);
                    } finally {
                        latch.countDown();
                    }

                });
            }
            latch.await();

            CouponResponseDto couponById = couponService.findCouponById(couponId);
//            List<CouponByUserResponseDto> couponListByUser = couponUserService.findCouponListByUser(
//                currentMemberId, null);

            // then
            //assertThat(couponListByUser.size()).isEqualTo(100);
            assertThat(couponById.issuedQuantity()).isEqualTo(threadCount);
        }
    }
}