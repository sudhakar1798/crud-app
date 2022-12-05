package io.sudhakar.student.service.impl;

import io.sudhakar.student.entity.UserDetailsEntity;
import io.sudhakar.student.dto.User;
import io.sudhakar.student.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    public JwtUserDetailsService(UserDetailsRepository userDetailsRepository) {

        this.userDetailsRepository = userDetailsRepository;
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserDetailsEntity> userDetails = userDetailsRepository.findByUsername(username);

        return new User(userDetails.get().getUsername(), userDetails.get().getPassword(), userDetails.get().getId(), new ArrayList<>());

    }

}
