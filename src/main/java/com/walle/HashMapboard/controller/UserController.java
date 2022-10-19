package com.walle.HashMapboard.controller;

import com.walle.HashMapboard.config.auth.PrincipalDetails;
import com.walle.HashMapboard.domain.user.User;
import com.walle.HashMapboard.domain.user.UserRepository;
import com.walle.HashMapboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final UserService userService;

    // login, logout 엔드포인드를 만들지않아도 된다. Security 에서 제공하는 /login 을 사용하면된다.

    // form 로그인과, OAuth 로그인 둘 다 PrincipalDetails로 받을 수 있도록 설계 (public class PrincipalDetails implements UserDetails, OAuth2User)
    // Authentication으로 유저 정보를 받지않는 이유는 form, OAuth 로그인에 따라 캐스팅을 달리 해줘야하기 때문
    @ResponseBody
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("PrincipalDetails.getUser() = " + principalDetails.getUser());

        if(principalDetails.getAttributes() == null)
            log.info("Form 로그인");

        if(principalDetails.getAttributes() != null)
            log.info("OAuth 로그인");

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
    public String loginForm(
            @RequestParam(value = "error", required = false)String error,
            @RequestParam(value = "exception", required = false)String exception,
            Model model ) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "loginForm";
    }

    //회원가입
    @PostMapping("/join")
    public String join(User user) {
        userService.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @ResponseBody
    @GetMapping("/info")
    public String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @ResponseBody
    @GetMapping("/data")
    public String data(){
        return "데이터 정보";
    }

    @ResponseBody
    @GetMapping("/form/login")
    public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 일반 로그인 캐스팅

        // 동일한 결과값, User(id=1, username=ssar, password=$2a$..., email=ssar@naver.com, role=ROLE_USER, provider=null, providerId=null, createDate=2022-06-27)
        log.info("authentication.getPrincipal() = " + principalDetails);
        log.info("userDetails = " + userDetails);

        return "일반 로그인 세션 정보 확인하기";
    }

    @ResponseBody
    @GetMapping("/oauth/login")
    public String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); // OAuth 로그인 캐스팅

        // 동일한 결과 값, {sub=123456789, name=홍길동, given_name=길동, family_name=홍, picture=https://..., email=test@gmail.com, email_verified=true, locale=ko}
        log.info("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        log.info("oauth.getAttributes()" + oauth.getAttributes());

        return "OAuth 로그인 세션 정보 확인하기";
    }
}
