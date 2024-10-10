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
        AddressDto addressDto = readAddressCsv.findAddressByDistrict(request.city(),request.street(),request.district());
        UserDto dto = UserDto.from(request,bCryptPasswordEncoder.encode(request.password()),addressDto.code());
        userRepository.save(User.from(dto));
        return dto.to();
    }

//    @Override
//    public UserDto getUserDetailsByUserId(String userName) {
//        User user = userRepository.findByUserId(userName)
//                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
//        return user.to();
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User auth = userRepository.findByUserId(userName)
//                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
//
//        // UserRole을 List<GrantedAuthority>로 변환
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new UserRoleCustom(auth.getRole())); // UserRole을 GrantedAuthority로 추가
//
//        return new org.springframework.security.core.userdetails.User(auth.getUserId(), auth.getPassword(), true,true,true,true,authorities);    // new ArrayList -> 권한 추가
//    }

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
        // TODO :: 주소 변경에 따른 코드 번호 받아오는 로직 작성할 예정
        user.update(request,null);

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
