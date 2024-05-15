package com.example.newsBlock.security;

import com.example.newsBlock.Exception.RefreshTokenException;
import com.example.newsBlock.Exception.ValidException;
import com.example.newsBlock.entity.RefreshToken;
import com.example.newsBlock.entity.Users;
import com.example.newsBlock.entity.enumurated.RoleType;
import com.example.newsBlock.mapper.UserMapper;
import com.example.newsBlock.security.jwt.JwtUtils;
import com.example.newsBlock.service.impl.RefreshTokenService;
import com.example.newsBlock.service.impl.UsersServiceImpl;
import com.example.newsBlock.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UsersServiceImpl usersService;
    private final PasswordEncoder passwordEncoder;


    public AuthResponse authenticateUser(UpsertUserDTO upsertUserDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(upsertUserDTO.getEmail(), upsertUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return AuthResponse.builder().id(userDetails.getId()).token(jwtUtils.generateJwtToken(userDetails)).refreshToken(refreshToken.getToken())
                .email(userDetails.getUsername()).roles(roles).build();
    }
    public Users register(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersService.save(users);
    }
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String requestRefresh= refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByRefreshToken(requestRefresh).map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userid->{
                    Users tokenOwner = usersService.findById(userid).orElseThrow(()->new RefreshTokenException("Exception trying to get token for userId: {}"+ userid));
                    String token = jwtUtils.generateTokenFromUserName(tokenOwner.getEmail());
                    return new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userid).getToken());
                }).orElseThrow(()->new RefreshTokenException(requestRefresh,"Refresh token not found"));
    }
    public void isUser(Users users) {
        if(isUserRole()&& !Objects.equals(getCurrentId(), users.getId())){
            throw new ValidException("Access denied");
        }
        if(isModerator() && users.getRoles().stream().anyMatch(role -> role.getAuthority().equals(RoleType.ROLE_ADMIN))){
            throw new ValidException("Access denied");
        }
    }
    public void logout(){
        var currentPrincipal = getAuthentication().getPrincipal();
        if(currentPrincipal instanceof AppUserDetails userDetails){
            Long userId= userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public boolean isAdmin(){
        return getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_ADMIN".equals(grantedAuthority.getAuthority()));
    }
    public boolean isModerator(){
        return getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_MODERATOR".equals(grantedAuthority.getAuthority()));
    }
    public boolean isUserRole(){
        return getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_USER".equals(grantedAuthority.getAuthority()));
    }
    public Long getCurrentId(){
        Authentication authentication = getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof AppUserDetails){
            var details = (AppUserDetails) authentication.getPrincipal();
            return details.getId();
        }
        return null;
    }

}
