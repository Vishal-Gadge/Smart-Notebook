package com.dangerarmy.auth_service.service;


import com.dangerarmy.auth_service.model.UserModel;
import com.dangerarmy.auth_service.model.UserRoles;
import com.dangerarmy.auth_service.repo.UserRepo;
import com.dangerarmy.auth_service.repo.UserRolesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepo userRepo;
    private final UserRolesRepo userRolesRepo;
    private final PasswordEncoder passwordEncoder;


    public void addAdmin(UserModel admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        UserModel dbAdmin = userRepo.save(admin);
        userRolesRepo.save(new UserRoles(null,dbAdmin,"USER"));
        userRolesRepo.save(new UserRoles(null,dbAdmin,"ADMIN"));
        log.info("Admin was added with email :{}",admin.getEmail());
    }
}
