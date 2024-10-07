package com.webest.user.application.service;

import com.webest.user.domain.model.User;
import com.webest.user.domain.model.vo.UserDto;
import com.webest.user.domain.repository.UserRepository;
import com.webest.user.exception.UserErrorCode;
import com.webest.user.exception.UserException;
import com.webest.user.presentation.dto.request.UserUpdateRequest;
import com.webest.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    // findByUserId
    public User findByUserId(String userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
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
