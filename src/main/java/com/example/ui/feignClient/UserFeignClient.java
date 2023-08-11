package com.example.ui.feignClient;

import com.common.ExamDTOs.ScoreDTO;
import com.common.JWTDTOs.JWTRequest;
import com.common.QuestionDTOs.QuestionAndScore;
import com.common.UserDTOs.UserDTO;
import com.common.UserDTOs.UserDTOSession;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PostMapping("/users/authentication/get-teacher-id")
    List<Long> getTeacherId(@RequestParam("id") Long id);
    @PostMapping("/users/authentication/get-admin-id")
    Long getAdminId(@RequestParam("id") Long id);

    @PostMapping("/users/authentication/save-score")
    void saveScore(@RequestBody ScoreDTO scoreDTO);

    @GetMapping("/users/authentication/get-all-students")
    List<UserDTO> getAllStudents();

    @GetMapping("/users/authentication/get-score-of-student")
    List<ScoreDTO> getScoreOfStudent(@RequestParam("id") Long id);

    @GetMapping("/users/authentication/get-question-score")
    int questionScore(@RequestParam Long questionID);

    @GetMapping("/users/authentication/get-scores-and-questions")
    List<QuestionAndScore> getQuestionsAndScoreByExamID(@RequestParam Long examID, @RequestParam Long studentID);

    @GetMapping("/users/authentication/get-exams-of-student")
    List<Long> getExamsOfStudent(@RequestParam Long studentID);

    @GetMapping("/users/authentication/get-total-of-student")
    int getTotal(@RequestParam Long examId, @RequestParam Long userID);

}
