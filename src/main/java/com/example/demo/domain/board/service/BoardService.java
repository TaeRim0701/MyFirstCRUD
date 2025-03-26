package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.BoardRequestDto;
import com.example.demo.domain.board.dto.BoardResponseDto;
import com.example.demo.domain.board.entity.BoardEntity;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createOneBoard(BoardRequestDto dto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(dto.getTitle());
        boardEntity.setContent(dto.getContent());

        boardRepository.save(boardEntity);
        
        // User와 Board 연관 관계 생성
        
        // 현재 게시글을 작성하는 유저
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 해당 유저의 Entity 가져오기
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        
        // 연관 관계 만드는 매소드 호출
        userEntity.addBoardEntity(boardEntity);
        userRepository.save(userEntity);
    }

    // 게시글 하나 읽기
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public BoardResponseDto readOneBoard(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow();

        BoardResponseDto dto = new BoardResponseDto();
        dto.setId(boardEntity.getId());
        dto.setTitle(boardEntity.getTitle());
        dto.setContent(boardEntity.getContent());

        return dto;
    }
    
    // 게시글 모두 읽기
    public List<BoardResponseDto> readAllBoards() {
        List<BoardEntity> list = boardRepository.findAll();
        List<BoardResponseDto> dtos = new ArrayList<>();

        for (BoardEntity boardEntity : list) {
            BoardResponseDto dto = new BoardResponseDto();
            dto.setId(boardEntity.getId());
            dto.setTitle(boardEntity.getTitle());
            dto.setContent(boardEntity.getContent());

            dtos.add(dto);
        }
        return dtos;
    }

    // 게시글 하나 수정
    @Transactional
    public void updateOneBoard(Long id, BoardRequestDto dto) {

        // 기존 id에 대한 게시글 데이터 불러오기
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow();

        // 게시글 dto -> entity
        boardEntity.setTitle(dto.getTitle());
        boardEntity.setContent(dto.getContent());
        boardRepository.save(boardEntity);
    }

    @Transactional
    public void deleteOneBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
