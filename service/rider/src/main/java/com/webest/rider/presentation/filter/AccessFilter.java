package com.webest.rider.presentation.filter;

import static com.webest.web.common.CommonStaticVariable.X_USER_ID;
import static com.webest.web.common.CommonStaticVariable.X_USER_ROLE;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String userId = request.getHeader(X_USER_ID);
        String userRole = request.getHeader(X_USER_ROLE);

        if (StringUtils.hasText(userId) && StringUtils.hasText(userRole)) {

//            setAuthentication(Long.valueOf(userId), userRole);
        }

        filterChain.doFilter(request, response);

    }

//    public void setAuthentication(Long userId, Strng role) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = createAuthentication(userId, role);
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//    }
//
//    private Authentication createAuthentication(Long userId, String role) {
//        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(simpleGrantedAuthority);
//
//        return new UsernamePasswordAuthenticationToken(
//            new UserAuthInfo(userId, authorities, role), null,
//            authorities);
//    }i
}
