package com.example.ui.controllers;

import com.common.ExamDTOs.*;
import com.common.Image.ImageCompressor;
import com.common.JWTDTOs.JWTRequest;
import com.common.QuestionDTOs.QuestionDTO;
import com.common.QuestionDTOs.QuestionType;
import com.common.SubjectDTOs.SubjectDTO;
import com.common.UserDTOs.PersonDTO;
import com.common.UserDTOs.StudentDTO;
import com.common.UserDTOs.UserDTO;
import com.common.UserDTOs.UserDTOSession;
import com.example.ui.service.UIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
@Slf4j
@RequestMapping("/ui")
@RequiredArgsConstructor
public class UIController {

    private final UIService uiService;
    private String examName;
    private int questionsSize = 0;
    private int questionNumber = 0;
    private List<QuestionDTO> questionDTOList = new ArrayList<>();
    private ExamDTO examDTO = new ExamDTO();

    /** This method is used to return the login page
     * @param request
     * @return login page
     */

    @GetMapping("/")
    public ModelAndView login(@ModelAttribute JWTRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    /** This method is used to return the login page
     * @param request
     * @return login page
     */
    @PostMapping("/redirecting")
    public ModelAndView redirecting(@ModelAttribute JWTRequest request) {
        log.info("User: {}", request.getEmail() + " Pass :  " + request.getPassword());
        String redirectTo = uiService.login(request);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/");
            modelAndView.addObject("message", "Wrong email or password");
        }
        return modelAndView;
    }

    /** This method is used to return the data gotten from signup page
     * @param user
     * @return signup page
     */
    @PostMapping("/create-user")
    public ModelAndView createUser(@ModelAttribute UserDTO user) {
        log.info("User: {}", user.getName()+ " Email :  "+ user.getEmail() + " Pass :  " + user.getPassword());
        String redirectTo = uiService.createUser(user);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/");
            modelAndView.addObject("message", "invalid email or password");
        }
        return modelAndView;
    }

    /** This method is used to return the token page
     * @return token page
     */
    @GetMapping("/verify-token")
    public ModelAndView verifyToken() {
        ModelAndView modelAndView = new ModelAndView("token-entry");
        return modelAndView;
    }

    /** This method is used to verify the token and return to appropriate page
     * @param token
     * @return dashboard page
     */
    @PostMapping("/verification")
    public ModelAndView verifyToken(@RequestParam("token") String token) {
        String redirectTo = uiService.verifyToken(token);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/verify-token");
            modelAndView.addObject("message", "Token Not right");
        }
        return modelAndView;
    }

    /** This method is used to return the dashboard page
     * @return dashboard page
     */
    @GetMapping("/welcome")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView("welcome");
        List<ExamObject> approvedExams = uiService.approvedExams();

        modelAndView.addObject("approvedExams", approvedExams);
        List<ExamObject> unapprovedExams = uiService.unapprovedExams();

        modelAndView.addObject("unapprovedExams", unapprovedExams);
        return modelAndView;
    }

    /** This method is used to return add student page
     * @return add student page
     */
    @GetMapping("/add-student")
    public ModelAndView addStudent(ModelAndView modelAndView){

        modelAndView = new ModelAndView("add-student");
        UserDTO user = new UserDTO();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    /** This method is used to save student in the database
     * @param name student name gotten from the form in the add student page
     * @param email student email gotten from the form in the add student page
     * @param password student password gotten from the form in the add student page
     * @param image student image gotten from the form in the add student page
     * @return ModelAndView
     */
    @PostMapping("/save-student")
    public ModelAndView saveStudent(@RequestParam("name") String name,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("displayPicture") MultipartFile image) {
        String redirectTo = uiService.saveStudent(name, email, password, image);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/welcome");
            modelAndView.addObject("message", "Error Creating Student's Account");
        }
        return modelAndView;
    }

    /** This method is used to return add teacher page
     * @return add-teacher page
     */
    @GetMapping("/add-teacher")
    public ModelAndView addTeacher(ModelAndView modelAndView){

        modelAndView = new ModelAndView("add-teacher");
        UserDTO user = new UserDTO();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    /** This method is used to save teacher in the database
     * @param user teacher details gotten from the form in the add teacher page
     * @return ModelAndView
     */
    @PostMapping("/save-teacher")
    public ModelAndView createTeacher(@ModelAttribute UserDTO user) {
        log.info("User: {}", user.getName()+ " Email :  "+ user.getEmail() + " Pass :  " + user.getPassword());
        String redirectTo = uiService.saveTeacher(user);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/welcome");
            modelAndView.addObject("message", "Error Creating Teacher's Account");
        }
        return modelAndView;
    }

    /** This method is used to return student dashboard page
     * @return add-exam page
     */
    @GetMapping("/student-dashboard")
    public ModelAndView welcomeStudent() {
        ModelAndView modelAndView = new ModelAndView("student-dashboard");
        UserDTOSession sessionData = uiService.getSessionData();

        byte [] decompressedImage = ImageCompressor.decompressImage(sessionData.getUser().getDisplayPicture());

        String studentImage = ImageCompressor.convertImageIntoString(decompressedImage);

        StudentDTO studentDTO = new StudentDTO(sessionData.getUser().getName(), studentImage, sessionData.getUser().getEmail());

        modelAndView.addObject("studentDTO", studentDTO);

        return modelAndView;
    }

    /** This method is used to return exam page
     * @return exam page
     */
    @GetMapping("/exams")
    public ModelAndView exam() {
        ModelAndView modelAndView = new ModelAndView("exam");

        List<ExamDTO> exams = uiService.getExamList();
        Long id = uiService.getSessionData().getUser().getId(); // student ID

        Long adminID = uiService.getAdminID(id);

        List<Long> ids = uiService.getTeachersByAdminID(adminID); //Teacher IDs

        List<ExamDTO> filteredList = new ArrayList<>();

        for (ExamDTO exam : exams) {
            if (ids.contains(exam.getTeacherID())) {
                if(exam.isApproved()) {
                    filteredList.add(exam);
                }
            }
        }
        modelAndView.addObject("exams", filteredList);
        return modelAndView;
    }

    /** This method is used to return change password
     * @return change password page
     */
    @GetMapping("/change-password")
    public ModelAndView changePassword() {
        ModelAndView modelAndView = new ModelAndView("password");
        return modelAndView;
    }

    /** This method is used to sign out
     * @return redirects to ui page
     */
    @PostMapping("/sign-out")
    public ModelAndView signOut(){
        ModelAndView modelAndView = new ModelAndView("redirect:/ui/");
        modelAndView.addObject("request", new JWTRequest());
        return modelAndView;
    }

    /** This method is used to return teacher dashboard page
     * @return teacher dashboard page
     */
    @GetMapping("/teacher-dashboard")
    public ModelAndView welcomeTeacher() {
        ModelAndView modelAndView = new ModelAndView("teacher-dashboard");
        UserDTOSession sessionData = uiService.getSessionData();
        PersonDTO personDTO = new PersonDTO(sessionData.getUser().getId(), sessionData.getUser().getName(), sessionData.getUser().getEmail());
        modelAndView.addObject("personDTO", personDTO);
        return modelAndView;
    }

    /** This method is used to create subject
     * @return String
     */
    @PostMapping("/create-subject")
    public String createSubject(@RequestParam("subjectName") String subjectName){
        log.info(" Subject Name :  {} ",subjectName);
        String redirectTo =  uiService.saveSubject(subjectName);
        return redirectTo;
    }

    /** This method is used to create exam
     * @return ModelAndView
     */
    @GetMapping("/create-exam")
    public ModelAndView createExam(ModelAndView modelAndView){

        modelAndView = new ModelAndView("exam-page");
        List<SubjectDTO> subjectList = uiService.getSubjects();
        modelAndView.addObject("subjectList", subjectList);
        log.info("Subject List : {} ", subjectList.get(0).getSubjectName());
        Exam exam = new Exam();
        modelAndView.addObject("exam", exam);
        return modelAndView;

    }

    /** This method is used to save exam
     * @return ModelAndView
     */
    @PostMapping("/send-exam")
    public ModelAndView sendExam(@ModelAttribute Exam exam)
    {
        UserDTOSession sessionData = uiService.getSessionData();
        Long id = sessionData.getUser().getId();
        exam.setTeacherID(id);
        exam.setApproved(false);
        examName = exam.getExamTitle();
        log.info("Exam : {} ", exam);
        String redirectTo =  uiService.saveExam(exam);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/teacher-dashboard");
            modelAndView.addObject("message", "Error Creating Exam");
        }
        return modelAndView;

    }
    /** This method is used to load create questions
     * @return create-questions page
     */
    @GetMapping("/create-questions")
    public ModelAndView createQuestionnaire()
    {
        ModelAndView modelAndView = new ModelAndView("create-questions");
        return modelAndView;
    }
    /** This method is used to save questions the database
     * @param questionDTO : QuestionDTO - object containing question details
     * @return ModelAndView
     */
    @PostMapping("/save-questions")
    public ModelAndView saveQuestionnaire(@ModelAttribute QuestionDTO questionDTO)
    {
        log.info("Question : {} ", questionDTO.getQuestionScore());
        questionDTO.setExamID(uiService.getExam(examName));
        questionDTO.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        String redirectTo =  uiService.saveQuestion(questionDTO);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/teacher-dashboard");
            modelAndView.addObject("message", "Error Creating Questionnaire");
        }
        return modelAndView;
    }

    /** This method is used to save questions and finish the exam
     * @param questionDTO : QuestionDTO - object containing question details
     * @return ModelAndView
     */
    @PostMapping("/save-and-finish")
    public ModelAndView saveQuestionnaireAndFinish(@ModelAttribute QuestionDTO questionDTO)
    {
        log.info("Question : {} ", questionDTO);
        questionDTO.setExamID(uiService.getExam(examName));
        questionDTO.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        uiService.saveQuestion(questionDTO);
        ModelAndView modelAndView = new ModelAndView("teacher-dashboard");
        UserDTOSession sessionData = uiService.getSessionData();
        PersonDTO personDTO = new PersonDTO(sessionData.getUser().getId(), sessionData.getUser().getName(), sessionData.getUser().getEmail());
        modelAndView.addObject("personDTO", personDTO);
        return modelAndView;
    }

    /** This method is used to return exam page and save the exam as approved
     * @param examID : Long - exam ID
     * @return exam page
     */
    @PostMapping("/approve-exam")
    public ModelAndView approveExam(@RequestParam("examID") Long examID)
    {
        log.info("Exam ID : {} ", examID);
        String redirectTo =  uiService.approveExam(examID);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/welcome");
            modelAndView.addObject("message", "Error Approving Exam");
        }
        List<ExamObject> approvedExams = uiService.approvedExams();
        modelAndView.addObject("approvedExams", approvedExams);
        List<ExamObject> unapprovedExams = uiService.unapprovedExams();
        modelAndView.addObject("unapprovedExams", unapprovedExams);
        return modelAndView;
    }

    /** This method is used to return exam page and save the exam as unapproved
     * @param examID : Long - exam ID
     * @return exam page
     */
    @PostMapping("/unapprove-exam")
    public ModelAndView unapproveExam(@RequestParam("examID") Long examID)
    {
        log.info("Exam ID : {} ", examID);
        String redirectTo =  uiService.unapproveExam(examID);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        if(redirectTo.equals("error"))
        {
            modelAndView.addObject("error", "/ui/welcome");
            modelAndView.addObject("message", "Error Unapproving Exam");
        }
        List<ExamObject> approvedExams = uiService.approvedExams();
        modelAndView.addObject("approvedExams", approvedExams);
        List<ExamObject> unapprovedExams = uiService.unapprovedExams();
        modelAndView.addObject("unapprovedExams", unapprovedExams);
        return modelAndView;
    }

    /** This method is used to return the attempt exam page
     * @param id : Long - exam ID
     * @return exam page
     */
    @PostMapping("/attempt-exam")
    public ModelAndView attemptExam(@RequestParam Long id)
    {
        examDTO = uiService.getExamById(id);
        ModelAndView modelAndView = new ModelAndView("attempt-exam");
        log.info("Exam ID : {} ", examDTO.getId());
        questionDTOList = uiService.getQuestionList(examDTO.getId());
        questionsSize = questionDTOList.size();
        log.info("Size : {} ", questionsSize);
        modelAndView.addObject("exam", examDTO);
        modelAndView.addObject("question", questionDTOList.get(questionNumber));
        questionNumber++;
        return modelAndView;
    }

    /** This method is used to return the next question
     * @return exam page
     */
    @PostMapping("/submit-exam")
    public ModelAndView submitQuestion(@RequestParam("answer") String answer, @RequestParam("id") Long questionID){

        int score = 0;
        if(answer.equals(questionDTOList.get(questionNumber-1).getAnswer()))
        {
            score = questionDTOList.get(questionNumber-1).getQuestionScore();
        }
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setExamID(examDTO.getId());
        scoreDTO.setQuestionID(questionID);
        scoreDTO.setScore(score);
        scoreDTO.setStudentID(uiService.getSessionData().getUser().getId());
        uiService.saveScore(scoreDTO);


        if(questionNumber == questionsSize)
        {
            ModelAndView modelAndView = new ModelAndView("redirect:/ui/student-dashboard");
            score=0;
            questionNumber=0;
            questionsSize=0;
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("attempt-exam");
        modelAndView.addObject("exam", examDTO);
        modelAndView.addObject("question", questionDTOList.get(questionNumber++));

        return modelAndView;
    }

    /** This method is used to return the student under a particular admin
     * @return student dashboard page
     */
    @GetMapping("/students")
    public ModelAndView getStudents()
    {
        ModelAndView modelAndView = new ModelAndView("students");
        List<UserDTO> students = uiService.getAllStudents();
        UserDTOSession sessionData = uiService.getSessionData();
        List<UserDTO> filteredStudents = students
                .stream()
                .filter(student -> student.getAdminID()
                        .equals(sessionData
                                .getUser()
                                .getId()))
                .collect(Collectors.toList());
        modelAndView.addObject("students", filteredStudents);
        return modelAndView;
    }

    /** This method is used to view the records of the student
     * @return exam page
     */
    @GetMapping("/view-records")
    public ModelAndView viewRecords(@RequestParam Long id)
    {
        ModelAndView modelAndView = new ModelAndView("view-records");
        log.info("Student ID : {} ", id);
        List<Long> examIDs = uiService.getExamsOfStudent(id);
        log.info("Exam IDs : {} ", examIDs.size());
        List<ExamRecord> examRecords = new ArrayList<>();
        for (Long examID: examIDs) {
            ExamRecord examRecord = new ExamRecord();
            examRecord.setExamName(uiService.getExamById(examID).getExamTitle());
            examRecord.setExamID(examID);
            examRecord.setTotal(uiService.getTotal(examID, id));
            log.info("Total : {} ", examRecord.getTotal());
            examRecord.setQuestionsAndScores(uiService.getQuestionsAndScoreByExamID(examID, id));
            examRecords.add(examRecord);
        }
        modelAndView.addObject("exams", examRecords);
        return modelAndView;
    }

}
