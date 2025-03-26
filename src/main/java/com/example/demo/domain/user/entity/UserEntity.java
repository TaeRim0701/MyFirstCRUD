package com.example.demo.domain.user.entity;

import com.example.demo.domain.board.entity.BoardEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true) // 계정이 삭제되면 그 계정이 작성한 글 삭제
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    // 유저에 대해 새로운 글을 추가할 떄 : 추가할 글을 받아서 연관관계에 매핑
    public void addBoardEntity(BoardEntity entity) {
        entity.setUserEntity(this);
        boardEntityList.add(entity);
    }

    // 유저에 대해 기존 글을 삭제할 때 : 삭제할 글을 받아서 연관관계에서 뺌
    public void removeBoardEntity(BoardEntity entity) {
        entity.setUserEntity(null);
        boardEntityList.remove(entity);
    }
}
