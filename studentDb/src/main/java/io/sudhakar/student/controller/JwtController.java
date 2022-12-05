package io.sudhakar.student.controller;

import io.sudhakar.student.dto.MyUserDetails;
import io.sudhakar.student.dto.AuthenticationResponse;
import io.sudhakar.student.dto.User;
import io.sudhakar.student.service.impl.JwtUserDetailsService;
import io.sudhakar.student.util.JwtUtil;

import io.sudhakar.student.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class JwtController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;
    private final UserUtil userUtil;

    public JwtController(AuthenticationManager authenticationManager, JwtUserDetailsService userDetailsService, JwtUtil jwtTokenUtil, UserUtil userUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userUtil = userUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody MyUserDetails myUserDetails) throws Exception {

        try {
            log.info("api = /authenticate, method = POST, result = IN_PROGRESS");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(myUserDetails.getUsername(),
                            myUserDetails.getPassword()));

            final User userDetails = userDetailsService
                    .loadUserByUsername(myUserDetails.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            log.info("api = /authenticate, method = POST, result = SUCCESS");
            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            log.info("api = /authenticate, method = POST, result = ERROR",e);
            throw new Exception("Incorrect user name / password", e);
        }
    }
}