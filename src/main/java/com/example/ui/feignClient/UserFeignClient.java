package com.example.ui.feignClient;

import com.common.JWTRequest;
import com.common.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping("/users/authentication/login")
    String login(@RequestBody JWTRequest request);

    @PostMapping("/users/authentication/create-user")
    String createUser(@RequestBody UserDTO user);

    @PostMapping("/users/authentication/verification")
    String verifyToken(@RequestParam("token") String token);

    @PostMapping("/users/authentication/save-student")
    String saveStudent(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("displayPicture") MultipartFile image);
}
