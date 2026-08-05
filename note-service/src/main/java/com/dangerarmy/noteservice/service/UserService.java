package com.dangerarmy.noteservice.service;

import com.dangerarmy.noteservice.dto.UserDto;
import com.dangerarmy.noteservice.model.MyUserDetails;
import com.dangerarmy.noteservice.service.Impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JwtServiceImpl jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = new UserDto();
        return new MyUserDetails(user);
    }
}
