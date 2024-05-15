package com.example.newsBlock.web.model;

import com.example.newsBlock.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Getter
@Setter
@FieldNameConstants
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
}
