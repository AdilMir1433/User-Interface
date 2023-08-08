package com.example.ui.service;

import com.common.*;
import com.example.ui.feignClient.ExamFeignClient;
import com.example.ui.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UIService {

    private final UserFeignClient userFeignClient;
    private final ExamFeignClient examFeignClient;

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
    public String saveTeacher(UserDTO user) { return userFeignClient.saveTeacher(user);}

    public UserDTOSession getSessionData() {
        return userFeignClient.getSessionData();
    }
    public List<SubjectDTO> getSubjects(){
        return examFeignClient.getAllSubjects();
    }

    public String saveSubject(String subject){
        return examFeignClient.createSubject(subject);
    }
    public String saveExam(ExamDTO exam){
        return examFeignClient.saveExam(exam);
    }
    public String saveQuestion(QuestionDTO question){
        return examFeignClient.saveQuestion(question);
    }
    public Long getExam(String examName){
        Long id = examFeignClient.getExam(examName);
        log.info("Exam ID: {}", id);
        return id;
    }

}
