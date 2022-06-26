package com.walle.HashMapboard.controller;

import com.walle.HashMapboard.domain.user.User;
import com.walle.HashMapboard.domain.user.UserRepository;
import com.walle.HashMapboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @ResponseBody
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @ResponseBody
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @ResponseBody
    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    //회원가입 페이지
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    //로그인 페이지
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    //회원가입
    @PostMapping("/join")
    public String join(User user) {
        userService.save(user);
        return "redirect:/loginForm";
    }

    // login 엔드포인드를 만들지않아도 된다. Security 에서 제공하는 /login 을 사용하면된다.

}
