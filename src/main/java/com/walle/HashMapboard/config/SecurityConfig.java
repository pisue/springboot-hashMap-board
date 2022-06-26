package com.walle.HashMapboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(밑에 SecurityConfig)가 스프링 기본 필터체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePw(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증된(로그인 된) 사용자의 접근을 허용
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()

                .formLogin().loginPage("/loginForm") // 권한이 없는 페이지로 가면 자동으로 /login페이지로 전환된다.
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 security가 낚아채서 대신 로그인을 진행해준다.
                .defaultSuccessUrl("/"); //로그인이 성공하면 "/"페이지 호출

    }
}
