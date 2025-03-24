package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String nickname);
    Optional<UserEntity> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
