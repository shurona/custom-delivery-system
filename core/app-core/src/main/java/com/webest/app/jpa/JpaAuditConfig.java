package com.webest.app.jpa;

import static com.webest.web.common.CommonStaticVariable.X_USER_ID;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        ServletRequestAttributes attributes
            = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        //TODO: null일 때 어떻게 처리해야 하지
        String userId = null;
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String userIdHeader = request.getHeader(X_USER_ID);
            if (userIdHeader != null) {
                userId = userIdHeader;
            }
        }

        return Optional.ofNullable(userId);
    }
}
