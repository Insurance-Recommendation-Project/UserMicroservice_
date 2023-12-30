package com.example.userr.repositories;

import com.example.userr.entities.Token;
import com.example.userr.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Boolean existsByUser(Users user);

    Optional<Token> findByUser(Users user);

    Optional<Token> findByUserAndToken(Users user, String token);

    Optional<Token> findByToken(String token);
}
