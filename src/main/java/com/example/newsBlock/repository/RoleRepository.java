package com.example.newsBlock.repository;

import com.example.newsBlock.entity.Role;
import com.example.newsBlock.entity.enumurated.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByAuthority(RoleType roleType);
}
