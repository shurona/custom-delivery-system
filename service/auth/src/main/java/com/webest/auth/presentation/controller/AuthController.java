package com.webest.auth.presentation.controller;

import com.webest.auth.application.AuthService;
import com.webest.auth.infrastructure.email.MailSendService;
import com.webest.auth.infrastructure.email.dto.EmailCheckDto;
import com.webest.auth.infrastructure.email.dto.EmailRequest;
import com.webest.auth.presentation.dto.request.JoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import com.webest.web.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MailSendService mailService;

    @PostMapping("/signUp")
    public CommonResponse<JoinResponse> createUser(@RequestBody @Valid JoinRequest joinRequest) {
        JoinResponse response = authService.create(joinRequest);

        return CommonResponse.success(response);
    }


    @PostMapping ("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequest emailDto){
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :"+emailDto.email());
        return mailService.joinEmail(emailDto.email());
    }

    @PostMapping("/mailCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto dto){
        Boolean Checked=mailService.CheckAuthNum(dto.email(),dto.authNum());
        if(Checked){
            return "인증이 완료되었습니다.";
        }
        else{
            throw new NullPointerException("인증 정보가 잘 못 되었습니다 다시 인증해주세요");
        }
    }
}
