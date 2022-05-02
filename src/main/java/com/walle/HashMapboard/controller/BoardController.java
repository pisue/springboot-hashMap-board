package com.walle.HashMapboard.controller;

import com.walle.HashMapboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /*@RequestMapping(value = "/")
    public String home() {
        return "index";
    }*/

    @RequestMapping(value = "/board/boardList")
    public String boardList() {
        return "/board/boardList";
    }

    @RequestMapping(value = "/board/boardView")
    public String boardView() {
        return "/board/boardView";
    }

    @RequestMapping(value = "/board/boardWrite")
    public String boardWrite() {
        return "/board/boardWrite";
    }
}
