package com.dangerarmy.auth_service.repo;

import com.dangerarmy.auth_service.model.UserModel;
import com.dangerarmy.auth_service.model.VerifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyUserRepo extends JpaRepository<VerifyUser, Integer> {

    VerifyUser findByToken(String token);
    VerifyUser findByUserModel(UserModel userModel);
}
