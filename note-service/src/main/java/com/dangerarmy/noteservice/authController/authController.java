package com.dangerarmy.noteservice.authController;

import com.dangerarmy.noteservice.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class authController {

    @Autowired
    private AuthClient authClient;

    @GetMapping("/check-health")
    public String checkAuthHealth(){
        String message = authClient.checkHealth();
        System.out.println("message from auth client is: "+message);
        return "auth client message "+message;
    }
}
