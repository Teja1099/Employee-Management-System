package com.prokarma.nike.controller;

import com.prokarma.nike.entity.Interview;
import com.prokarma.nike.entity.Leave;
import com.prokarma.nike.repository.InterviewRepository;
import com.prokarma.nike.services.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/interviews")
    public List<Interview> getInterviews() {
        return interviewService.getInterviews();
    }

    @GetMapping("/interviewData/{month}/{year}")
    public List<List<String>> getInterviewData (
            @PathVariable("month") String month,
            @PathVariable("year") Integer year
            )
    {
        List<List<String>> li =interviewService.getMonthlyInterviewData(month,year);

        System.out.println(li.size());
        return li;
    }

//    @GetMapping("interviews/bar/{month}/{year}")
//    public Map<String,Float> getInterviewAnalysisData (
//            @PathVariable("month") String month,
//            @PathVariable("year") Integer year
//    )
//    {
//        Map<String,Float> li =interviewService.getMonthlyInterviewAnalysisData(month,year);
//
//        System.out.println(li.size());
//        return li;
//    }

    @GetMapping("/interviews/bar/{month}/{year}")
    public Map<String,String> getInterviewAnalysisData (
            @PathVariable("month") String month,
            @PathVariable("year") Integer year
    )
    {
        Map<String,String> li = interviewService.getMonthlyInterviewAnalysisData(month,year);

        System.out.println(li.size());
        return li;
    }


    @GetMapping("/interviews/bar")
    public Map<String,String> getInterviewAnalysisData ()
    {
        Map<String,String> li =interviewService.getInterviewAnalysisData();

        System.out.println(li.size());
        return li;
    }

    @PostMapping("/interview")
    public ResponseEntity<?> addInterview(@RequestBody Interview interview) {
        //leaveService.addLeave(leave);
        interviewService.addInterview(interview);
        System.out.println(interview.toString());
        return  ResponseEntity.ok(interview);
    }



}
