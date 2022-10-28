package com.doohee.mediaserver.service;

import com.doohee.mediaserver.dto.LoginDto;
import com.doohee.mediaserver.dto.TokenDto;
import com.doohee.mediaserver.dto.UserDto;
import com.doohee.mediaserver.entity.Authority;
import com.doohee.mediaserver.entity.User;
import com.doohee.mediaserver.exception.DuplicateMemberException;
import com.doohee.mediaserver.jwt.TokenProvider;
import com.doohee.mediaserver.repository.AuthorityRepository;
import com.doohee.mediaserver.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Transactional
    public UserDto signup(UserDto userDto){
        if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null)!=null){
            throw new DuplicateMemberException("UserId Already exists");
        }

        Authority authority = new Authority("ROLE_USER");
        User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .nickname(userDto.getNickname())
                    .authorities(Collections.singleton(authority))
                    .build();

        return UserDto.from(userRepository.save(user));
    }
    public TokenDto login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        return new TokenDto(jwt);
    }
    @Transactional
    public void deleteAll(){
        userRepository.deleteAll();
    }

}
