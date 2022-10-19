package com.walle.HashMapboard.config.auth;

import com.walle.HashMapboard.domain.user.User;
import com.walle.HashMapboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;


@RequiredArgsConstructor
@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "해당 유저는 존재하지 않습니다.";

        } else if(exception instanceof BadCredentialsException) {
            User user = userRepository.findByUsername(request.getParameter("username"));

            if(user != null){
                if(user.getRetry() >= 5){
                    user.setAccountNonLocked(false);
                    errorMessage = "5회이상 비밀번호를 잘못입력하셔서 계정 잠금처리가 되었습니다.";
                }else{
                    user.setRetry(user.getRetry() + 1);
                    errorMessage = "비밀번호가 틀립니다. " + user.getRetry() + "회 잘못입력하셨습니다.";
                }
            }

        } else if(exception instanceof LockedException) {
            errorMessage = "잠긴 계정입니다.";

        } else if(exception instanceof DisabledException) {
            errorMessage = "비활성화 계정입니다.";

        } else if(exception instanceof AccountExpiredException) {
            errorMessage = "만료된 계정입니다.";

        } else if(exception instanceof CredentialsExpiredException) {
            errorMessage = "비밀번호가 만료되었습니다.";
        } else{
            errorMessage = "알 수 없는 이유로 로그인 에러가 발생하였습니다.";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        response.sendRedirect("/loginForm?error=true&exception=" + errorMessage);
    }
}
