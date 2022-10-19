package com.walle.HashMapboard.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username; // OAuth 의 경우라면 "google(facebook)_115245822843643743849"
    private String password; // OAuth 의 경우라면 "google(facebook)_115245822843643743849" 해싱값
    private String email;
    private String role;

    // OAuth 를 회원가입을 위한 변수
    private String provider; // ex) "google"
    private String providerId; // ex) sub=115245822843643743849, 제공업체의 유저 식별자

    private boolean isAccountNonExpired = true;  // 계정 만료 상태
    private boolean isAccountNonLocked = true;  // 계정 잠금 상태
    private boolean isCredentialsNonExpired = true; // 패스워드 만료 상태
    private boolean isEnabled = true;  // 계정 사용가능 상태
    private Integer retry = 0; // 로그인 시도횟수

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @Builder
    public User(String username, String password, String email, String role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
