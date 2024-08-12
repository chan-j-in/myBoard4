package com.example.board4.controller;

import com.example.board4.dto.BoardRequestDto;
import com.example.board4.dto.BoardResponseDto;
import com.example.board4.dto.SuccessResponseDto;
import com.example.board4.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/posts")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }

    @PostMapping("/api/post")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto requestDto) {
        return boardService.createPost(requestDto);
    }

    @GetMapping("/api/post/{id}")
    public BoardResponseDto getPost(@PathVariable("id") Long id) {
        return boardService.getPost(id);
    }

    @PutMapping("/api/post/{id}")
    public BoardResponseDto updatePost(@PathVariable("id") Long id, @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.updatePost(id, requestDto);
    }

    @DeleteMapping("/api/post/{id}")
    public SuccessResponseDto deletePost(@PathVariable("id") Long id, @RequestBody String password) throws Exception {
        return boardService.deletePost(id, password);
    }
}
