package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.users.JwtResponse;
import com.codecool.codekickfc.dto.users.LoginRequest;
import com.codecool.codekickfc.exceptions.UserNotFoundException;
import com.codecool.codekickfc.repository.UserRepository;
import com.codecool.codekickfc.repository.model.User;
import com.codecool.codekickfc.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );


        User user = userRepository.findByUsername(loginRequest.username()).orElseThrow(
                UserNotFoundException::new
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();


        return new JwtResponse(jwt, userDetails.getUsername(), user.getId(), roles);
    }

    public Map<String, Object> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Map<String, Object> userInfo = new HashMap<>();
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                    UserNotFoundException::new
            );
            userInfo.put("username", userDetails.getUsername());
            userInfo.put("authorities", userDetails.getAuthorities());
            userInfo.put("userId", user.getId());
            return userInfo;
        }
        return null;
    }
}
