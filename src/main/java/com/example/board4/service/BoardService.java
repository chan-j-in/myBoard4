package com.example.board4.service;

import com.example.board4.dto.BoardRequestDto;
import com.example.board4.dto.BoardResponseDto;
import com.example.board4.dto.SuccessResponseDto;
import com.example.board4.entity.Board;
import com.example.board4.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        return boardRepository.findById(id).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (!requestDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다");

        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public SuccessResponseDto deletePost(Long id, String password) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );

        if (password.equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        boardRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }
}
