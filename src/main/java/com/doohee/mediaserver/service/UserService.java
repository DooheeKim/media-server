package com.doohee.mediaserver.service;

import com.doohee.mediaserver.dto.UserDto;
import com.doohee.mediaserver.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signup(UserDto userDto){
        if(userRepository.findOneWithAuthoritiesByUserId(userDto.getUserId()).orElse(null)!=null){

        }
    }

}
