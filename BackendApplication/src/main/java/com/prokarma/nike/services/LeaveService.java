package com.prokarma.nike.services;

import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.entity.Leave;
import com.prokarma.nike.repository.EmployeeRepository;
import com.prokarma.nike.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Leave>   getLeaves(){
        return leaveRepository.findAll();
    };

    public List<Leave> getLeavesByMonth(String month) {
        return leaveRepository.findAllByMonth(month);
    }

    public List<List<String >> getLeaveMonthlyData(String month){
        List<Leave> leaves = leaveRepository.findAllByMonth(month);
        List<List<String >> result =new ArrayList<>();
        HashMap<String,List<String>> map = new HashMap<>();

        for (Leave leave : leaves) {
            if(leave.getDays().equals("")) continue;
            if(map.containsKey(leave.getName())){
                String temp="";
                temp+="<br/>"+"Month  :"+leave.getMonth()+"<br/>";
                // calculating the dates
                List<String> list = Arrays.asList( leave.getDays().split(",") );
                List<Integer> newList = list.stream()
                        .map(s -> Integer.parseInt(s))
                        .collect(Collectors.toList());
                temp+="Leaves Took  "+newList.size()+"<br/>";
                temp+="Dates  "+newList.toString()+"<br/>";
                List<String> li = map.get(leave.getName());
                temp=li.get(1)+temp;
                li.remove(1);
                li.add(temp);
                map.put(leave.getName(),li);
            }
            else{
                List<String> li = new ArrayList<>();
                String temp="";
                li.add(leave.getName());
                temp+="Month  :"+leave.getMonth()+"<br/>";
                // calculating the dates

                List<String> list = Arrays.asList( leave.getDays().split(",") );
                List<Integer> newList = list.stream()
                        .map(s -> Integer.parseInt(s))
                        .collect(Collectors.toList());
                temp+="Leaves Took  "+newList.size()+"<br/>";
                temp+="Dates  "+newList.toString()+"<br/>";

                li.add(temp);
                System.out.println(temp);
                map.put(leave.getName(),li);
            }
        }

        for (Map.Entry<String,List<String>> entry : map.entrySet()) {
            for (String str :
                    entry.getValue()) {
                System.out.println(str);
            }
           result.add(entry.getValue());
        }
        return  result;
    }

    public Map<String,Integer> getLeaveMonthlyBarData(String month, Integer year) {
        Map<String,Integer> map = new HashMap<>();
        List<Leave> leaves = leaveRepository.findAllByMonthAndYear(month,year);
        System.out.println(leaves.size()+"working");
        for (Leave leave : leaves) {
            if(leave.getDays().equals("")) continue;

            if (map.containsKey(leave.getName())) {
                List<String> list = Arrays.asList( leave.getDays().split(",") );
                map.put(leave.getName(), map.get(leave.getName())+list.size());

            } else {
                List<String> list = Arrays.asList( leave.getDays().split(",") );
                map.put(leave.getName(),list.size());
            }
        }
        return map;
    }

    public Map<String,Integer> getLeaveBarData() {
        Map<String,Integer> map = new HashMap<>();
        List<Leave> leaves = leaveRepository.findAll();
        System.out.println(leaves.size()+"working");
        for (Leave leave : leaves) {
            if(leave.getDays().equals("")) continue;

            if (map.containsKey(leave.getName())) {
                List<String> list = Arrays.asList( leave.getDays().split(",") );
                map.put(leave.getName(), map.get(leave.getName())+list.size());

            } else {
                List<String> list = Arrays.asList( leave.getDays().split(",") );
                map.put(leave.getName(),list.size());
            }
        }
        return map;
    }

    public void addLeave(Leave leave) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        leave.setDays(leave.getDate().getDate()+"");
        leave.setMonth(months[leave.getDate().getMonth()]);
        leave.setYear(1900+leave.getDate().getYear());
        Employee employee = employeeRepository.findByEmployeeId(leave.getEmpId());
        List<Leave> leaves = employee.getLeaves();
        leaves.add(leave);
        employee.setLeaves(leaves);
        System.out.println(leave.toString());
        employeeRepository.save(employee);
    }
}
