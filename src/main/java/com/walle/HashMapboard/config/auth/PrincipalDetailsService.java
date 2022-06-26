package com.walle.HashMapboard.config.auth;


import com.walle.HashMapboard.domain.user.User;
import com.walle.HashMapboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정 loginProcessingUrl("/login")
// /login 요청이 들어오면 스프링 빈에 등록된 UserDetailsService loadUserByUsername 함수가 실행된다.
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인 페이지에서 유저 아이디의 파라미터는 반드시 input name="username"로 설정
    // SecurityConfig 에서 usernameParameter("userId")로 바꿀 수 있지만, 가급적 기본값 username 을 사용
    // return 값은 '시큐리티 session(내부 Authentication(내부 UserDetails))' Authentication 에 들어간다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            return null;
        }

        return new PrincipalDetails(user);
    }
}
