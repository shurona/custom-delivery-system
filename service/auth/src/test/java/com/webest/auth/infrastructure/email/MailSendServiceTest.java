package com.webest.auth.infrastructure.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webest.auth.infrastructure.redis.RedisUtil;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class MailSendServiceTest {

    private final String email = "abc1234@abcde.abc";
    private final int AUTH_SIZE = 6;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private RedisUtil redisUtil;
    @InjectMocks
    private MailSendService mailSendService;

    @BeforeEach
    public void setUp() {
        mailSendService = new MailSendService(mailSender, redisUtil, email);
    }

    @Test
    public void 랜덤_나오나요() {
        // given
        // when
        mailSendService.makeRandomNumber();
        int authNumberOne =
            (int) ReflectionTestUtils.getField(mailSendService, "authNumber");

        mailSendService.makeRandomNumber();
        int authNumberTwo =
            (int) ReflectionTestUtils.getField(mailSendService, "authNumber");

        // then

        assertThat(authNumberOne).isLessThan(1000000);
        assertThat(authNumberTwo).isLessThan(1000000);
        assertThat(authNumberOne).isNotEqualTo(authNumberTwo);


    }

    @Test
    public void 메일전송_성공() {
        // given

        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        doNothing().when(mailSender).send(mimeMessage);

        doNothing().when(redisUtil).setDataExpire(anyString(), anyString(), eq(60 * 5L));

        // when
        String rand = mailSendService.joinEmail(email);

        // then
        assertThat(rand.length()).isLessThanOrEqualTo(AUTH_SIZE);

    }

    @Nested
    @DisplayName("AuthNum 테스트")
    class AuthNumTest {

        @Test
        public void 어스_테스트_정상() {
            // given
            String auth = "auth";
            when(redisUtil.getData(auth)).thenReturn(email);

            // when
            boolean b = mailSendService.CheckAuthNum(email, "auth");

            // then
            assertThat(b).isTrue();
        }

        @Test
        public void 어스_테스트_틀린경우() {
            // given
            String auth = "auth";
            when(redisUtil.getData(auth)).thenReturn(email + "::");

            // when
            boolean b = mailSendService.CheckAuthNum(email, "auth");

            // then
            assertThat(b).isFalse();
        }

        @Test
        public void 어스_테스트_널인경우() {
            // given
            String auth = "auth";
            when(redisUtil.getData(auth)).thenReturn(null);

            // when
            boolean b = mailSendService.CheckAuthNum(email, "auth");

            // then
            assertThat(b).isFalse();
        }

    }

}