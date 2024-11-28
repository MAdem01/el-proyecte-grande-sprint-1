package com.codecool.codekickfc.security.service;

import com.codecool.codekickfc.repository.UserRepository;
import com.codecool.codekickfc.repository.model.Role;
import com.codecool.codekickfc.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getType()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), roles
        );
    }
}
