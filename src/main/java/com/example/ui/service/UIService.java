package com.example.ui.service;

import com.common.JWTRequest;
import com.common.UserDTO;
import com.example.ui.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UIService {

    private final UserFeignClient userFeignClient;

    public String login(JWTRequest request ) {
        return userFeignClient.login(request);
    }

    public String createUser(UserDTO user) {
        return userFeignClient.createUser(user);
    }

    public String verifyToken(String token) {
        return userFeignClient.verifyToken(token);
    }

    public String saveStudent(String name, String email, String password, MultipartFile image) {
        return userFeignClient.saveStudent(name, email, password, image);
    }

}
