package com.webest.coupon.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.webest.coupon.domain.dtos.CouponByUserDto;
import com.webest.coupon.domain.dtos.CouponCheckData;
import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.DiscountType;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.request.CouponUpdateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponByUserResponseDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = SPRING)
public interface CouponMapper {

    @Mapping(source = "createdAt", target = "createTime")
    CouponResponseDto couponToDto(Coupon coupon);

    @Mapping(source = "requestDto.startTime", target = "startTime", qualifiedByName = "localDateToLocalDateTime")
    @Mapping(source = "requestDto.endTime", target = "endTime", qualifiedByName = "localDateToLocalDateTime")
    CouponCheckData createDtoToCouponCreationData(CouponCreateRequestDto requestDto);

    @Mapping(source = "requestDto.startTime", target = "startTime", qualifiedByName = "localDateToLocalDateTime")
    @Mapping(source = "requestDto.endTime", target = "endTime", qualifiedByName = "localDateToLocalDateTime")
    CouponCheckData updateDtoToCouponCreationData(CouponUpdateRequestDto requestDto,
        DiscountType discountType, Integer discountValue);


    List<CouponByUserResponseDto> couponByUserDtoToResponseDto(
        List<CouponByUserDto> couponByUserDto);

    /* ======================================================================
     * custom default
     ======================================================================*/
    @Named("localDateToLocalDateTime")
    default LocalDateTime localDateToLocalDateTime(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }
}
