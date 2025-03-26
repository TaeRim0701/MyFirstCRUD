package com.example.demo.domain.board.entity;

import com.example.demo.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")//content에 더 많은 데이터 작성할 수 있도록
    private String content;

    @ManyToOne
    private UserEntity userEntity;

}
