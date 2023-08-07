package com.example.ui.feignClient;

import com.common.JWTRequest;
import com.common.UserDTO;
import com.common.UserDTOSession;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping("/users/authentication/login")
    String login(@RequestBody JWTRequest request);

    @PostMapping("/users/authentication/create-user")
    String createUser(@RequestBody UserDTO user);

    @PostMapping("/users/authentication/verification")
    String verifyToken(@RequestParam("token") String token);

    @PostMapping(value = "/users/authentication/save-student", consumes = {"multipart/form-data"}, headers = "Content-Type= multipart/form-data")
    String saveStudent(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestPart("displayPicture") MultipartFile image);

    @PostMapping(value = "/users/authentication/save-teacher")
    String saveTeacher(@RequestBody UserDTO user);
    @GetMapping("/users/authentication/sessionData")
    UserDTOSession getSessionData();
}
