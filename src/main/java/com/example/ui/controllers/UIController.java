package com.example.ui.controllers;

import com.common.*;
import com.example.ui.service.UIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@org.springframework.stereotype.Controller
@Slf4j
@RequestMapping("/ui")
@RequiredArgsConstructor
public class UIController {

    private final UIService uiService;
    private String examName;

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
    @GetMapping("/add-teacher")
    public ModelAndView addTeacher(ModelAndView modelAndView){

        modelAndView = new ModelAndView("add-teacher");
        UserDTO user = new UserDTO();
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @PostMapping("/save-teacher")
    public ModelAndView createTeacher(@ModelAttribute UserDTO user) {
        log.info("User: {}", user.getName()+ " Email :  "+ user.getEmail() + " Pass :  " + user.getPassword());
        String redirectTo = uiService.saveTeacher(user);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;
    }
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
    @GetMapping("/exams")
    public ModelAndView exam() {
        ModelAndView modelAndView = new ModelAndView("exam");
        return modelAndView;
    }
    @GetMapping("/change-password")
    public ModelAndView changePassword() {
        ModelAndView modelAndView = new ModelAndView("password");
        return modelAndView;
    }
    @PostMapping("/sign-out")
    public ModelAndView signOut(){
        ModelAndView modelAndView = new ModelAndView("redirect:/ui/");
        modelAndView.addObject("request", new JWTRequest());
        return modelAndView;
    }
    @GetMapping("/teacher-dashboard")
    public ModelAndView welcomeTeacher() {
        ModelAndView modelAndView = new ModelAndView("teacher-dashboard");
        UserDTOSession sessionData = uiService.getSessionData();
        PersonDTO personDTO = new PersonDTO(sessionData.getUser().getId(), sessionData.getUser().getName(), sessionData.getUser().getEmail());
        modelAndView.addObject("personDTO", personDTO);
        return modelAndView;
    }

    @PostMapping("/create-subject")
    public String createSubject(@RequestParam("subjectName") String subjectName){
        log.info(" Subject Name :  {} ",subjectName);
        String redirectTo =  uiService.saveSubject(subjectName);
        return redirectTo;
    }

    @GetMapping("/create-exam")
    public ModelAndView createExam(ModelAndView modelAndView){

        modelAndView = new ModelAndView("exam-page");
        List<SubjectDTO> subjectList = uiService.getSubjects();
        modelAndView.addObject("subjectList", subjectList);
        log.info("Subject List : {} ", subjectList.get(0).getSubjectName());
        ExamDTO exam = new ExamDTO();
        modelAndView.addObject("exam", exam);
        return modelAndView;

    }
    @PostMapping("/send-exam")
    public ModelAndView sendExam(@ModelAttribute ExamDTO exam)
    {
        UserDTOSession sessionData = uiService.getSessionData();
        Long id = sessionData.getUser().getId();
        exam.setTeacherID(id);
        exam.setApproved(false);
        examName = exam.getExamTitle();
        log.info("Exam : {} ", exam);
        String redirectTo =  uiService.saveExam(exam);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;

    }
    @GetMapping("/create-questions")
    public ModelAndView createQuestionnaire()
    {
        ModelAndView modelAndView = new ModelAndView("create-questions");
        return modelAndView;
    }
    @PostMapping("/save-questions")
    public ModelAndView saveQuestionnaire(@ModelAttribute QuestionDTO questionDTO)
    {
        log.info("Question : {} ", questionDTO);
        questionDTO.setExamID(uiService.getExam(examName));
        questionDTO.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        String redirectTo =  uiService.saveQuestion(questionDTO);
        ModelAndView modelAndView = new ModelAndView(redirectTo);
        return modelAndView;
    }

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
}
