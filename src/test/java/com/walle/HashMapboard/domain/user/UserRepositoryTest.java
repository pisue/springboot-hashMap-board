package com.walle.HashMapboard.domain.user;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        String username = "ssar";
        String rawPassword = "1234";
        String encPassword = "";
        String email = "ssar@naver.com";

        encPassword = bCryptPasswordEncoder.encode(rawPassword);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encPassword);
        user.setEmail(email);
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void 유저찾기() {
        //given
        String username = "ssar";

        //when
        User findUser = userRepository.findByUsername(username);

        //then
        assertThat(findUser.getUsername()).isEqualTo(username);
        System.out.println("===================================");
        System.out.println(findUser);
    }
}