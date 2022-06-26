package com.walle.HashMapboard.config.auth;

import com.walle.HashMapboard.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session 을 만들어준다. (키값 Security ContextHolder)
// 시큐리티 세션에 들어갈 수 있는 정보의 Object 는 정해져있다 => Authentication 타입 객체
// Authentication 객체 안에는 User 정보가 있어야 한다.
// User 객체의 타입도 정해져 있다. => UserDetails 타입 객체

// Security Session -> Authentication -> UserDetails (우리가 만든 PrincipalDetails 객체에 UserDetails 를 implements)
@Data
public class PrincipalDetails implements UserDetails {

    private User user;

    //생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User 의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되었는가?
    // true 를 리턴하면 만료되지 않음을 의미
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는가?
    // true 를 리턴하면 계정이 잠겨있지 않음을 의미
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 패스워드가 만료되었는가?
    // true 를 리턴하면 패스워드가 만료되지 않음을 의미
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 사용가능한가?
    // true 를 리턴하면 사용가능한 계정을 의미
    @Override
    public boolean isEnabled() {
        return true;
    }
}
