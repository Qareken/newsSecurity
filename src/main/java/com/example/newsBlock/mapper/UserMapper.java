package com.example.newsBlock.mapper;

import com.example.newsBlock.entity.Users;
import com.example.newsBlock.web.model.UpsertUserDTO;
import com.example.newsBlock.web.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper(componentModel = "spring")

public interface UserMapper {

    UserDTO toDto(Users user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "news", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Users toEntity(UserDTO userDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Users toEntity(UpsertUserDTO upsertUserDTO);
    List<UserDTO> toDtoList(List<Users> users);
}
