package com.example.ui.controllers;

import com.common.JWTRequest;
import com.common.UserDTO;
import com.example.ui.service.UIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
@Slf4j
@RequestMapping("/ui")
@RequiredArgsConstructor
public class Controller {

    private final UIService uiService;

    @GetMapping("/")
    public ModelAndView login(@ModelAttribute JWTRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @PostMapping("/redirecting")
    public ModelAndView redirecting(@ModelAttribute JWTRequest request) {
        log.info("User: {}", request.getEmail() + " Pass :  " + request.getPassword());
        String redirectTo = uiService.login(request);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(@ModelAttribute UserDTO user) {
        log.info("User: {}", user.getName()+ " Email :  "+ user.getEmail() + " Pass :  " + user.getPassword());
        String redirectTo = uiService.createUser(user);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;
    }
    @GetMapping("/verify-token")
    public ModelAndView verifyToken() {
        ModelAndView modelAndView = new ModelAndView("token-entry");
        return modelAndView;
    }
    @PostMapping("/verification")
    public ModelAndView verifyToken(@RequestParam("token") String token) {
        String redirectTo = uiService.verifyToken(token);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;
    }
    @GetMapping("/welcome")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView("welcome");
        return modelAndView;
    }
    @GetMapping("/add-student")
    public ModelAndView addStudent(ModelAndView modelAndView){

        modelAndView = new ModelAndView("add-student");
        UserDTO user = new UserDTO();
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @PostMapping("/save-student")
    public ModelAndView saveStudent(@RequestParam("name") String name,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("displayPicture") MultipartFile image) {
        String redirectTo = uiService.saveStudent(name, email, password, image);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;
    }

}
