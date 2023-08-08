package com.example.ui.feignClient;

import com.common.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("exam-service")
public interface ExamFeignClient {
    @PostMapping("/exam/create-subject")
    String createSubject(@RequestParam("subjectName") String subjectName);

    @GetMapping("/exam/get-all-subjects")
    List<SubjectDTO> getAllSubjects();

    @PostMapping("/exam/save-exam")
    String saveExam(@RequestBody Exam exam);

    @PostMapping("/exam/save-question")
    String saveQuestion(@RequestBody QuestionDTO questionDTO);

    @GetMapping("/exam/get-exam")
    Long getExam(@RequestParam String  examName);

    @GetMapping("/exam/get-all-exams")
    List<ExamObject> getAllExams();

    @PostMapping("/exam/approve-exam")
    String approveExam(@RequestParam Long examId);

    @PostMapping("/exam/unapprove-exam")
    String unapproveExam(@RequestParam Long examId);

    @GetMapping("/exam/get-exam-list")
    List<ExamDTO> getExamDTOList();

    @GetMapping("/exam/get-questions-by-id")
    List<QuestionDTO> getExamById(@RequestParam Long examId);

}



