package com.walle.HashMapboard.config;

import com.walle.HashMapboard.config.auth.CustomAuthFailureHandler;
import com.walle.HashMapboard.config.auth.CustomAuthSuccessHandler;
import com.walle.HashMapboard.config.auth.PrincipalDetailsService;
import com.walle.HashMapboard.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 세션 >> 스프링 세션 >> Authentication (1. UserDetails[일반 로그인], 2. OAuth2User[OAuth 로그인])
// UserDetails와 OAuth2User를 상속하는 클래스를 만들어서 사용하면 컨트롤러에서

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(밑에 SecurityConfig)가 스프링 기본 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// secured 어노테이션 활성화 @Secured("ROLE_ADMIN") 권한 1개 설정,
// preAuthorize(메서드 호출전 권환체크), PostAuthorize(메서드 호출 후 권환체크) 어노테이션 활성화 (메서드 호출 전 권한확인, 2개이상 권한설정)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthFailureHandler customAuthFailureHandler;

    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler;

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Bean
    public BCryptPasswordEncoder encodePw(){
        return new BCryptPasswordEncoder();
    }

    // setHideUserNotFoundExceptions를 설정해주기위해 선언
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
        impl.setUserDetailsService(principalDetailsService);
        impl.setPasswordEncoder(encodePw());
        impl.setHideUserNotFoundExceptions(false) ;
        return impl;
    }

    // OAuth 로그인이 완료된 뒤 후처리가 필요
    // 1.코드받기(인증), 2. 코드를 통해서 엑세스토큰(사용자 정보의 접근 권한),
    // 3.사용자 프로필 정보를 가져와서 정보를 토대로 자동으로 회원가입을 진행
    // 4.추가적인 정보가 필요하다면 별도의 회원가입 진행이 필요함
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증된(로그인 된) 사용자의 접근을 허용
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()

                .formLogin().loginPage("/loginForm") // Form 로그인 설정, (참고로 권한이 없는 페이지로 가면 자동으로 /login 페이지로 전환된다.)
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 security 가 낚아채서 대신 로그인을 진행해준다.
                .successHandler(customAuthSuccessHandler)
                .failureHandler(customAuthFailureHandler) // 로그인 실패 핸들러
                .defaultSuccessUrl("/")

                .and()

                .oauth2Login().loginPage("/loginForm")
                .userInfoEndpoint().userService(principalOauth2UserService); // oauth2 로그인 시 customOAuth2UserService에서 설정을 진행하겠다라는 의미

    }
}


