package com.webest.coupon.application;


import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.domain.dtos.CouponCheckData;
import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.CouponSearchCondition;
import com.webest.coupon.domain.repository.CouponQueryRepository;
import com.webest.coupon.domain.repository.CouponRepository;
import com.webest.coupon.domain.service.CouponDomainService;
import com.webest.coupon.mapper.CouponMapper;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.request.CouponUpdateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import com.webest.web.exception.ApplicationException;
import com.webest.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;

    private final CouponDomainService couponDomainService;

    private final CouponMapper couponMapper;

    /**
     * 쿠폰 생성
     */
    @Transactional
    public Long createCoupon(CouponCreateRequestDto requestDto) {

        CouponCheckData couponCheckData = couponMapper.createDtoToCouponCreationData(
            requestDto);

        // validation 체크
        Coupon couponWithValidation = couponDomainService.checkValidCreateCoupon(couponCheckData,
            requestDto.content());
        Coupon cp = this.couponRepository.save(couponWithValidation);
        return cp.getCouponId();
    }

    /**
     * 쿠폰 아이디로 조회
     */
    @Override
    public CouponResponseDto findCouponById(Long id) {
        Coupon coupon = couponByIdAndCheck(id);
        return couponMapper.couponToDto(coupon);
    }

    /**
     * 쿠폰 업데이트
     */
    @Transactional
    @Override
    public CouponResponseDto updateCouponById(CouponUpdateRequestDto requestDto, Long id) {

        Coupon coupon = couponByIdAndCheck(id);

        CouponCheckData couponCheckData = couponMapper
            .updateDtoToCouponCreationData(requestDto, coupon.getDiscountType(),
                coupon.getDiscountValue());

        // 검증
        couponDomainService.checkValidUpdateCoupon(couponCheckData,
            coupon.getContent());

        coupon.updateCoupon(
            requestDto.content(),
            requestDto.duration(),
            requestDto.dateType(),
            requestDto.startTime().atStartOfDay(),
            requestDto.endTime().atStartOfDay(),
            requestDto.maxQuantity()
        );

        return couponMapper.couponToDto(coupon);
    }

    @Override
    public Page<CouponResponseDto> findCouponList(
        CouponSearchCondition condition, Pageable pageable) {

        Page<Coupon> riderListByQuery = couponQueryRepository.findCouponListByQuery(
            condition, pageable);

        return riderListByQuery.map(couponMapper::couponToDto);

    }

    @Transactional
    @Override
    public Long deleteCouponById(Long id) {
        Coupon coupon = couponByIdAndCheck(id);
        coupon.disableCoupon();
        return coupon.getCouponId();
    }

    /* ==========================================================================================
        private method
    ==========================================================================================*/
    private Coupon couponByIdAndCheck(Long id) {
        return couponRepository.findById(id).orElseThrow(() ->
            new CouponException(CouponErrorCode.COUPON_NOT_FOUND)
        );
    }
}
