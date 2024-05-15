package com.example.newsBlock.restController;

import com.example.newsBlock.Exception.EmailDuplicate;
import com.example.newsBlock.mapper.UserMapper;
import com.example.newsBlock.security.SecurityService;
import com.example.newsBlock.service.impl.UsersServiceImpl;
import com.example.newsBlock.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor

public class UserController {
    private final UsersServiceImpl usersService;

    private final SecurityService service;
    private  final UserMapper userMapper;




    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody UpsertUserDTO upsertUserDTO){
        return ResponseEntity.ok(service.authenticateUser(upsertUserDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO>createUser(@Valid @RequestBody UpsertUserDTO upsertUserDTO){

        if(usersService.existsByUsername(upsertUserDTO.getFirstName()))
        {
            throw new EmailDuplicate("username already exist");
        }
        if(usersService.existsByEmail(upsertUserDTO.getEmail())){
            throw new EmailDuplicate("email already exist");
        }
       var user= service.register(userMapper.toEntity(upsertUserDTO));
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    @PutMapping("/edit")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpsertUserDTO upsertUserDTO){
        var user = userMapper.toEntity(upsertUserDTO);
        return ResponseEntity.ok(userMapper.toDto(usersService.update(user)));
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        var user = usersService.findByEmail(email);
        usersService.delete(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(service.refreshToken(refreshTokenRequest));
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        var users = usersService.findAll();
        return ResponseEntity.ok(userMapper.toDtoList(users));
    }
    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        service.logout();
        return ResponseEntity.ok(new SimpleResponse("user logout. Username is: "+userDetails.getUsername()));
    }


}
