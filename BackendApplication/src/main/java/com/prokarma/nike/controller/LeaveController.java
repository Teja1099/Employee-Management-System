package com.prokarma.nike.controller;

import com.prokarma.nike.entity.Leave;
import com.prokarma.nike.services.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;


    @GetMapping("/leaves")
    public List<Leave>   getLeaves(){

        return leaveService.getLeaves();
    };

    @GetMapping("/leaves/{month}")
    public List<List<String>> getLeavesByMonth(@PathVariable("month") String month) {
        return leaveService.getLeaveMonthlyData(month);
    }

    @GetMapping("/leaves/bar/{month}/{year}")
    public Map<String,Integer> getLeaveMonthlyBarData(
            @PathVariable("month") String month,
            @PathVariable("year") Integer year
            ) {
        System.out.println(year+"year");
        return leaveService.getLeaveMonthlyBarData(month,year);
    }


    @GetMapping("/leaves/bar")
    public Map<String,Integer> getLeaveBarData() {
        return leaveService.getLeaveBarData();
    }

    @PostMapping("/leave")
    public ResponseEntity<?> addLeave(@RequestBody Leave leave) {
        leaveService.addLeave(leave);

        return  ResponseEntity.ok(leave);
    }


}
