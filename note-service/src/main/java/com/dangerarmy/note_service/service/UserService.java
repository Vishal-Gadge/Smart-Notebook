package com.dangerarmy.note_service.service;

import com.dangerarmy.note_service.dto.UserDto;
import com.dangerarmy.note_service.model.MyUserDetails;
import com.dangerarmy.note_service.service.Impl.JwtServiceImpl;
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
