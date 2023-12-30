package com.example.userr.repositories;

import com.example.userr.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    boolean existsByUsername(String username);

    @Override
    Optional<Users>findById(Long aLong);


}
