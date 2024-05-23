package com.example.newsBlock.restController;

import com.example.newsBlock.Exception.EmailDuplicate;
import com.example.newsBlock.Exception.EntityNotFoundException;
import com.example.newsBlock.entity.Role;
import com.example.newsBlock.entity.Users;
import com.example.newsBlock.entity.enumurated.RoleType;
import com.example.newsBlock.mapper.UserMapper;
import com.example.newsBlock.security.SecurityService;
import com.example.newsBlock.service.impl.UsersServiceImpl;
import com.example.newsBlock.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UsersServiceImpl usersService;

    private final SecurityService service;
    private  final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody UpsertUserDTO upsertUserDTO){
        log.info(upsertUserDTO+"warn");
        return ResponseEntity.ok(service.authenticateUser(upsertUserDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO>createUser(@Valid @RequestBody UpsertUserDTO upsertUserDTO, @RequestParam RoleType roleType){
        Role role = Role.from(roleType);
        upsertUserDTO.setRoles(List.of(Role.from(roleType)));

        if(usersService.existsByEmail(upsertUserDTO.getEmail())){
            throw new EmailDuplicate("email already exist");
        }
       var user= service.register(userMapper.toEntity(upsertUserDTO) , role);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    @PutMapping("/edit")
    public ResponseEntity<UserDTO> updateUser( @RequestBody UpsertUserDTO upsertUserDTO){
        var user = userMapper.toEntity(upsertUserDTO);
        if(user.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.toDto(usersService.findById(id).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("not found with {} id", id)))));
    }
    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        service.logout();
        return ResponseEntity.ok(new SimpleResponse("user logout. Username is: "+userDetails.getUsername()));
    }


}
