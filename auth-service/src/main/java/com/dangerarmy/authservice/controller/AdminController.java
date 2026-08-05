package com.dangerarmy.authservice.controller;

import com.dangerarmy.authservice.dto.AdminReqUsers;
import com.dangerarmy.authservice.model.UserModel;
import com.dangerarmy.authservice.repo.UserRepo;
import com.dangerarmy.authservice.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllUsers")
    public List<AdminReqUsers> getAllUsers(){
        return userRepo.getAllUsersExceptPass();
    }

    @GetMapping("/getAllAdmins")
    public List<AdminReqUsers> getAllAdmins(){
        return userRepo.getAllAdmins();
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<Map<String, String>> addAdmin(@RequestBody UserModel admin){
        try {
            adminService.addAdmin(admin);
            return ResponseEntity.ok(Map.of("message","Admin Saved Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error","Unfortunately Admin was not saved"));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyAdmin(){
        try{
            return ResponseEntity.ok(Map.of("message","You are now Admin"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error"," You are not Admin"));
        }
      }
}
