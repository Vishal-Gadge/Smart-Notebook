package com.dangerarmy.authservice.service;

import com.dangerarmy.authservice.dto.SignupRequest;
import com.dangerarmy.authservice.model.UserModel;
import com.dangerarmy.authservice.model.UserRoles;
import com.dangerarmy.authservice.model.VerifyUser;
import com.dangerarmy.authservice.repo.UserRepo;
import com.dangerarmy.authservice.repo.UserRolesRepo;
import com.dangerarmy.authservice.repo.VerifyUserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {

    private final UserRepo userRepo;
    private final UserRolesRepo userRolesRepo;
    private final VerifyUserRepo verifyUserRepo;
    private final PasswordEncoder encoder12;
    private final EmailService emailService;

    @Transactional
    public void signup(SignupRequest user){
        emailService.isValidEmail(user.getEmail());

        Optional<UserModel> existingAppUser = userRepo.findByEmail(user.getEmail());

        //first time signup, user not present
        if(existingAppUser.isEmpty()){
            //save user in db and get id
            UserModel saveUser = new UserModel();
            BeanUtils.copyProperties(user, saveUser);
            saveUser.setPassword(encoder12.encode(user.getPassword()));
            UserModel savedUser = userRepo.save(saveUser);

            //save role as user
            userRolesRepo.save(new UserRoles(null, savedUser , "USER"));

            //save verify user and send email for verification
            String verifyToken = emailService.generateToken();
            VerifyUser verifyUser = new VerifyUser(savedUser, verifyToken);
            verifyUserRepo.save(verifyUser);

            emailService.sendVerificationEmail(user.getEmail() , verifyToken);
            log.info("User successfully signup with email :{}",emailService.maskEmail(user.getEmail()));

        //if user already present
        }else{
            VerifyUser verifyUser = verifyUserRepo.findByUserModel(existingAppUser.orElseThrow());

            if(verifyUser == null){
                verifyUser = new VerifyUser();
            }

            if(verifyUser.isVerified()){
                log.warn("{} is trying to signup again when it is already verified", emailService.maskEmail(user.getEmail()));
                emailService.signupAttempted(user.getEmail());
                emailService.sendAlreadyVerified(user.getEmail());
                return;
            }

            //if user not verified
            //create new token with new expiry and save
            String verifyToken = emailService.generateToken();
            verifyUser.setToken(verifyToken);
            verifyUser.setExpiresAt(new Date(System.currentTimeMillis()+(1000*60*10)));
            verifyUserRepo.save(verifyUser);

            emailService.sendVerificationEmail(user.getEmail() , verifyToken);
            log.info("User again tried signup but was not verified email :{}",emailService.maskEmail(user.getEmail()));
        }
    }
}
