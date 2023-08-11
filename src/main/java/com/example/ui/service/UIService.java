package com.example.ui.service;

import com.common.ExamDTOs.Exam;
import com.common.ExamDTOs.ExamDTO;
import com.common.ExamDTOs.ExamObject;
import com.common.ExamDTOs.ScoreDTO;
import com.common.Exception.CommonException;
import com.common.JWTDTOs.JWTRequest;
import com.common.QuestionDTOs.QuestionAndScore;
import com.common.QuestionDTOs.QuestionDTO;
import com.common.SubjectDTOs.SubjectDTO;
import com.common.UserDTOs.UserDTO;
import com.common.UserDTOs.UserDTOSession;
import com.example.ui.feignClient.ExamFeignClient;
import com.example.ui.feignClient.UserFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UIService {

    private final UserFeignClient userFeignClient;
    private final ExamFeignClient examFeignClient;

    /**Method to send login request to user service
     * @param request - JWTRequest object containing username and password
     * @return String - view name
     */
    public String login(JWTRequest request) {
        try {
            return userFeignClient.login(request);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }

    /**Method to send logout request to user service
     * @param user - UserDTO object containing user data
     * @return String - view name
     */
    public String createUser(UserDTO user) {
        try {
            return userFeignClient.createUser(user);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }

    /**Method to send token for verification to user service
     * @param token - String containing JWT token
     * @return String - view name
     */
    public String verifyToken(String token) {
        try {
            return userFeignClient.verifyToken(token);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }

    /**Method to send request for user data to user service
     * @param email - String containing user email
     * @param password - String containing user password
     * @param image - MultipartFile containing user image
     * @return String - view name
     */
    public String saveStudent(String name, String email, String password, MultipartFile image) {
        try {
            return userFeignClient.saveStudent(name, email, password, image);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }

    /**Method to send user data to save it as a teacher
     * @param user - UserDTO object containing user data
     * @return String - view name
     */
    public String saveTeacher(UserDTO user) {
        try {
            return userFeignClient.saveTeacher(user);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }

    /**Method to get Session Data of current logged-in user
     * @return UserDTOSession
     */
    public UserDTOSession getSessionData() {
        try {
            return userFeignClient.getSessionData();
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }

    /**Method to get all subjects from user service
     * @return List<SubjectDTO>
     */
    public List<SubjectDTO> getSubjects(){
        try {
            return examFeignClient.getAllSubjects();
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }

    /**Method to send request to exam service to save Subject
     * @return String - view name
     */
    public String saveSubject(String subject){
        try {
            return examFeignClient.createSubject(subject);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }


    public String saveExam(Exam exam){
        try {
            return examFeignClient.saveExam(exam);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }
    public String saveQuestion(QuestionDTO question){
        try {
            return examFeignClient.saveQuestion(question);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }
    public Long getExam(String examName){
        try {
            Long id = examFeignClient.getExam(examName);
            log.info("Exam ID: {}", id);
            return id;
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public List<ExamObject> getAllExams(){
        try {
            return examFeignClient.getAllExams();
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public List<ExamObject> approvedExams(){
        try {
            List<ExamObject> exams = getAllExams();
            exams.removeIf(exam -> !exam.isApproved());
            return exams;
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public List<ExamObject> unapprovedExams(){
        try {
            List<ExamObject> exams = getAllExams();
            exams.removeIf(exam -> exam.isApproved());
            return exams;
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }

    public String approveExam(Long id){
        try {
            return examFeignClient.approveExam(id);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }
    public String unapproveExam(Long id){
        try {
            return examFeignClient.unapproveExam(id);
        }
        catch (CommonException e){
            return "error";
        }
        catch (FeignException e){
            return "error";
        }
    }

    public List<Long> getTeachersByAdminID(Long id){
        try {
            return userFeignClient.getTeacherId(id);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public Long getAdminID(Long id){
        try {
            return userFeignClient.getAdminId(id);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public List<ExamDTO> getExamList(){
        try {
            return examFeignClient.getExamDTOList();
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }

    public List<QuestionDTO> getQuestionList(Long examId){
        try {
            return examFeignClient.getExamById(examId);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }

    public ExamDTO getExamById(Long id){
        try {
            return examFeignClient.getSingleExamById(id);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public void saveScore(ScoreDTO score){
        try {
            userFeignClient.saveScore(score);
        }
        catch (CommonException e){
            log.info("Error: {}", e.getMessage());
        }
        catch (FeignException e){
            log.info("Error: {}", e.getMessage());
        }
    }

    public List<UserDTO> getAllStudents(){
        try {
            return userFeignClient.getAllStudents();
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public List<ScoreDTO> getScoreOfStudent(Long id){
        try {
            return userFeignClient.getScoreOfStudent(id);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public int questionScore(@RequestParam Long questionID)
    {
        try {
            return userFeignClient.questionScore(questionID);
        }
        catch (CommonException e){
            return 0;
        }
        catch (FeignException e){
            return 0;
        }
    }
    public List<QuestionAndScore> getQuestionsAndScoreByExamID(Long examID, Long studentID){
        try {
            return userFeignClient.getQuestionsAndScoreByExamID(examID, studentID);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public List<Long> getExamsOfStudent(Long studentID)
    {
        try {
            return userFeignClient.getExamsOfStudent(studentID);
        }
        catch (CommonException e){
            return null;
        }
        catch (FeignException e){
            return null;
        }
    }
    public int getTotal(Long examId, Long userID){
        try {
            return userFeignClient.getTotal(examId, userID);
        }
        catch (CommonException e){
            return 0;
        }
        catch (FeignException e){
            return 0;
        }
    }

    public void deleteExam(Long id){
        try {
            examFeignClient.deleteExam(id);
        }
        catch (CommonException e){
            log.info("Error: {}", e.getMessage());
        }
        catch (FeignException e){
            log.info("Error: {}", e.getMessage());
        }
    }
    public Boolean isCurrentDateTimeInRange(String startTime, String startDate, String endTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalTime parsedStartTime = LocalTime.parse(startTime, timeFormatter);
        LocalTime parsedEndTime = LocalTime.parse(endTime, timeFormatter);
        LocalDate parsedStartDate = LocalDate.parse(startDate, dateFormatter);

        LocalDateTime rangeStartDateTime = LocalDateTime.of(parsedStartDate, parsedStartTime);
        LocalDateTime rangeEndDateTime = LocalDateTime.of(parsedStartDate, parsedEndTime);

        if (currentDateTime.isAfter(rangeStartDateTime) && currentDateTime.isBefore(rangeEndDateTime)) {
            LocalTime currentTime = currentDateTime.toLocalTime();

            return currentTime.isAfter(parsedStartTime) && currentTime.isBefore(parsedEndTime);
        }

        return false;
    }



}
