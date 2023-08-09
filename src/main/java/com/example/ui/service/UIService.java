package com.example.ui.service;

import com.common.*;
import com.example.ui.feignClient.ExamFeignClient;
import com.example.ui.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String saveExam(Exam exam){
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
    public List<ExamObject> getAllExams(){
        return examFeignClient.getAllExams();
    }
    public List<ExamObject> approvedExams(){
        List<ExamObject> exams = getAllExams();
        exams.removeIf(exam -> !exam.isApproved());
        return exams;
    }
    public List<ExamObject> unapprovedExams(){
        List<ExamObject> exams = getAllExams();
        exams.removeIf(exam -> exam.isApproved());
        return exams;
    }

    public String approveExam(Long id){
        return examFeignClient.approveExam(id);
    }
    public String unapproveExam(Long id){
        return examFeignClient.unapproveExam(id);
    }

    public List<Long> getTeachersByAdminID(Long id){
        return userFeignClient.getTeacherId(id);
    }
    public Long getAdminID(Long id){
        return userFeignClient.getAdminId(id);
    }
    public List<ExamDTO> getExamList(){
        return examFeignClient.getExamDTOList();
    }

    public List<QuestionDTO> getQuestionList(Long examId){
        return examFeignClient.getExamById(examId);
    }

    public ExamDTO getExamById(Long id){
        return examFeignClient.getSingleExamById(id);
    }
    public void saveScore(ScoreDTO score){
        userFeignClient.saveScore(score);
    }

    public List<UserDTO> getAllStudents(){
        return userFeignClient.getAllStudents();
    }
    public List<ScoreDTO> getScoreOfStudent(Long id){
        return userFeignClient.getScoreOfStudent(id);
    }
    public int questionScore(@RequestParam Long questionID)
    {
        return userFeignClient.questionScore(questionID);
    }
    public List<QuestionAndScore> getQuestionsAndScoreByExamID( Long examID, Long studentID){
        return userFeignClient.getQuestionsAndScoreByExamID(examID, studentID);
    }
    public List<Long> getExamsOfStudent(Long studentID)
    {
        return userFeignClient.getExamsOfStudent(studentID);
    }
    public int getTotal(Long examId, Long userID){
        return userFeignClient.getTotal(examId, userID);
    }

}
