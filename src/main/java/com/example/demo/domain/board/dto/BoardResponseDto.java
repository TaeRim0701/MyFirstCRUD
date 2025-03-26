package com.example.demo.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
}
