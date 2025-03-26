package com.example.demo.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private Long id;
    private String title;
    private String content;
}
