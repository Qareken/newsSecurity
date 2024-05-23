package com.example.newsBlock.entity;

import com.example.newsBlock.entity.enumurated.RoleType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
@Table(name = "authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType authority;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Users user;
    public GrantedAuthority toAuthority(){
        return   new SimpleGrantedAuthority(authority.name());
    }
    public static Role from(RoleType roleType){
        var role = new Role();
        role.setAuthority(roleType);
        return role;
    }




}