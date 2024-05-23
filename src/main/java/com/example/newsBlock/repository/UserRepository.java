package com.example.newsBlock.repository;

import com.example.newsBlock.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<Users> findUsersByEmail(String email);
    Boolean existsByFirstName(String username);
    Boolean existsByEmail(String email);

}
