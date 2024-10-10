package com.webest.user.application.service;

import com.webest.app.address.csv.ReadAddressCsv;
import com.webest.app.address.service.AddressDto;
import com.webest.user.domain.model.User;
import com.webest.user.domain.model.vo.UserDto;
import com.webest.user.domain.repository.UserRepository;
import com.webest.user.exception.UserErrorCode;
import com.webest.user.exception.UserException;
import com.webest.user.presentation.dto.request.UserJoinRequest;
import com.webest.user.presentation.dto.request.UserUpdateRequest;
import com.webest.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReadAddressCsv readAddressCsv;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // findByUserId
    public User findByUserId(String userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    // 유저 생성
    @Override
    public UserResponse create(UserJoinRequest request) {
        // TODO :: null 일때 에러 처리 필요
        AddressDto addressDto = readAddressCsv.findAddressByDistrict(request.city(),request.street(),request.district());
        UserDto dto = UserDto.from(request,bCryptPasswordEncoder.encode(request.password()),addressDto.code());
        userRepository.save(User.from(dto));
        return dto.to();
    }

    // 유저 마이페이지 데이터 호출
    @Override
    public UserResponse getUser(String userId) {
        UserDto dto = findByUserId(userId).to();

        return dto.to();
    }

    // 유저 정보 수정
    @Override
    public UserResponse update(String userId, UserUpdateRequest request) {
        User user = findByUserId(userId);

        String city = user.getCity();
        String street = user.getStreet();
        String district = user.getDistrict();

        if(request.city()!=null){
            city = request.city();
        }
        if(request.street()!=null){
            street = request.street();
        }
        if(request.district()!=null){
            district = request.district();
        }

        AddressDto addressDto = readAddressCsv.findAddressByDistrict(city,street,district);
        user.update(request,addressDto.code());

        return user.to().to();
    }

    // 유저 정보 삭제
    @Override
    public void delete(String userId) {
        findByUserId(userId);
        userRepository.delete(userId);
    }

    // 유저 전부 출력 (관리자 기능)
    @Override
    public List<UserResponse> getUserByAll() {
        Iterable<User> users = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();

        users.forEach(u -> {
            result.add(u.to().to());
        });

        return result;
    }
}
