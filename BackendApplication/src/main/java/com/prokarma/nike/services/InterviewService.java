package com.prokarma.nike.services;
import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.entity.Interview;
import com.prokarma.nike.entity.Leave;
import com.prokarma.nike.repository.EmployeeRepository;
import com.prokarma.nike.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormatSymbols;
import java.util.*;

@Service
public class InterviewService {
    
    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Interview> getInterviews() {
        return interviewRepository.findAll();
    }

    public List<List<String>> getMonthlyInterviewData(String month,Integer year) {
        List<List<String>> result = new ArrayList<>();
        List<Interview> interviews = interviewRepository.findByMonthAndYear(month);
        for (Interview i : interviews) {
            System.out.println(i.toString());
        }
        System.out.println(month+".toString()"+year);
        Map<String, List<Integer>> map = new HashMap<>();
        for (Interview interview : interviews) {
            if (map.containsKey(interview.getName())) {
                List<Integer> li = map.get(interview.getName());
                li.set(0, li.get(0) + calculateTime(interview.getTimeSpent()));
                int count[] = calculateCount(interview.getCandidateStatus());
                li.set(1, li.get(1) + count[0]);
                li.set(2, li.get(2) + count[1]);
            } else {
                List<Integer> li = new ArrayList<>();

                //calcluate time spent on each interview
                li.add(calculateTime(interview.getTimeSpent()));
                // calculate no.of round and selected
                int count[] = calculateCount(interview.getCandidateStatus());
                li.add(count[0]);
                li.add(count[1]);

                map.put(interview.getName(), li);
            }

        }

        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            List<String> str = new ArrayList<>();
            str.add(entry.getKey());
            String temp = "";

            int count = 0;
            int tot = 0;
            for (Integer li : entry.getValue()) {
                switch (count++) {
                    case 0:
                        if (li / 60 != 0) {
                            temp += "Time Spent : " + (li / 60) + " Hour " + (li % 60) + " Minutes" + "<br/>";
                        } else {
                            temp += "Time Spent : " + (li % 60) + " Minutes" + "<br/>";
                        }
                        break;
                    case 1:
                        tot = li;
                        temp += "No.of interviews  : " + li + "<br/>";
                        break;
                    case 2:
                        temp += "Candidate Status  : " + li + " Select, " + (tot - li) + " Reject" + "<br/>";
                        break;
                }

            }
            str.add(temp);
            result.add(str);
            System.out.println(temp);
        }
        return result;
    }
//
//    public List<List<String>> getMonthlyInterviewData() {
//        List<List<String>> result = new ArrayList<>();
//        List<Interview > interviews = interviewRepository.findAll();
//
//        Map<String,List<List<Integer>>> map = new HashMap<>();
//        List<String> months = Arrays.asList(new DateFormatSymbols().getMonths());
//
//        for (Interview interview : interviews) {
//            if(map.containsKey(interview.getName())) {
//                boolean check=true;
//                for (List<Integer> li : map.get(interview.getName()))  {
//
//                    if(li.get(0)==months.indexOf(interview.getMonth())) {
//                        check=false;
//                        li.set(1,li.get(1)+calculateTime(interview.getTimeSpent()));
//                        int count[] =calculateCount(interview.getCandidateStatus());
//                        li.set(2,li.get(2)+count[0]);
//                        li.set(3,li.get(3)+count[1]);
//                        break;
//                    }
//                }
//                if(check) {
//                    List<Integer> temp = new ArrayList<>();
//                    temp.add(months.indexOf(interview.getMonth()));
//                    temp.add(calculateTime(interview.getTimeSpent()));
//                    int count[] =calculateCount(interview.getCandidateStatus());
//                    temp.add(count[0]);
//                    temp.add(count[1]);
//                    List<List<Integer>> lii =map.get(interview.getName());
//                    lii.add(temp);
//                    map.put(interview.getName(),lii);
//                }
//            }
//            else {
//                List<Integer> li = new ArrayList<>();
//                li.add(months.indexOf(interview.getMonth()));
//                //calcluate time spent on each interview
//                li.add(calculateTime(interview.getTimeSpent()));
//                // calculate no.of round and selected
//                int count[] =calculateCount(interview.getCandidateStatus());
//                li.add(count[0]);
//                li.add(count[1]);
//                List<List<Integer >> lii = new ArrayList<>();
//                lii.add(li);
//                map.put(interview.getName(),lii);
//            }
//        }
//
//        for (Map.Entry<String, List<List<Integer>>> entry: map.entrySet()) {
//            List<String> str = new ArrayList<>();
//            str.add(entry.getKey());
//            String temp = "";
//            for (List<Integer> li : entry.getValue()) {
//                temp += "Month  : " + months.get(li.get(0)) + "<br/>"
//                        + "No.of interviews  : " + li.get(2) + "<br/>";
//                if (li.get(1) / 60 != 0) {
//                    temp += "Time Spent : " + (li.get(1) / 60) + " Hour " + (li.get(1) % 60) + " Minutes" + "<br/>";
//                } else {
//                    temp += "Time Spent : " + (li.get(1) % 60) + " Minutes" + "<br/>";
//                }
//                temp += "Candidate Status  : " + li.get(3) + " Select, " + (li.get(2) - li.get(3)) + " Reject" + "<br/>";
//            }
//            str.add(temp);
//            result.add(str);
//            System.out.println(temp);
//        }
//        return  result;
//    }

    public int calculateTime(String input) {

        String lower = input.toLowerCase();
        String[] str = lower.split(" ");
        int time;
        System.out.println(str.length);
        if(str[1].charAt(0)=='h') {
            time = Integer.parseInt(str[0])*60;
            if(str.length==4) {
                time+=Integer.parseInt(str[2]);
            }
        }
        else {
            time=Integer.parseInt(str[0]);
        }
        return  time;
    }

    public int[] calculateCount(String input) {
        int selectCount =0;
        int count =0;
        input=input.toLowerCase();
        List<String> str = Arrays.asList(input.split("[, ]+"));
        for (String s:str) {
            if(s.equals("select")) selectCount++;
            else if(s.equals("reject")) count++;

        }
        count+=selectCount;
        return new int[]{count,selectCount};

    }

//    public Map<String,Float>getMonthlyInterviewAnalysisData(String month,Integer year) {
//
//        Map<String,Float> mapResult = new HashMap<>();
//        List<Interview> interviews = interviewRepository.findByMonthAndYear(month);
//
//        for (Interview interview : interviews) {
//            if (mapResult.containsKey(interview.getName())) {
//                mapResult.put(interview.getName(),mapResult.get (interview.getName())+(float) calculateTime(interview.getTimeSpent())/60);
//            } else {
//                mapResult.put(interview.getName(),(float) calculateTime(interview.getTimeSpent())/60);
//            }
//
//        }
//
//
//        return mapResult;
//    }

    public Map<String,String> getMonthlyInterviewAnalysisData(String month,Integer year) {
        Map<String,String> result = new HashMap<>();
        Map<String,List<Float>> mapResult = new HashMap<>();
        List<Interview> interviews = interviewRepository.findByMonthAndYear(month);

        for (Interview interview : interviews) {
            if (mapResult.containsKey(interview.getName())) {
                List<Float> li =mapResult.get (interview.getName());
                li.set(0, li.get(0)+(float) calculateTime(interview.getTimeSpent())/60);
                int count[] = calculateCount(interview.getCandidateStatus());
                li.set(1,li.get(1)+(float) count[0]);
                mapResult.put(interview.getName(),li);
            } else {
                List<Float> li =new ArrayList<>();
                li.add((float) calculateTime(interview.getTimeSpent())/60);
                int count[] = calculateCount(interview.getCandidateStatus());
                li.add((float) count[0]);
                mapResult.put(interview.getName(),li);
            }

        }
        for (Map.Entry<String,List<Float>> entry: mapResult.entrySet()) {

            String temp="";
            for (Float li : entry.getValue()) {
//                float f =li;
//                int i =(int)f;
                temp+=li+",";
            }

            result.put(entry.getKey(),temp.substring(0,temp.length()-1));
        }
        return result;
    }



    public Map<String,String> getInterviewAnalysisData() {
        Map<String,String> result = new HashMap<>();
        Map<String,List<Float>> mapResult = new HashMap<>();
        List<Interview> interviews = interviewRepository.findAll();

        for (Interview interview : interviews) {
            if (mapResult.containsKey(interview.getName())) {
                List<Float> li =mapResult.get (interview.getName());
                li.set(0, li.get(0)+(float) calculateTime(interview.getTimeSpent())/60);
                int count[] = calculateCount(interview.getCandidateStatus());
                li.set(1,li.get(1)+(float) count[0]);
                mapResult.put(interview.getName(),li);
            } else {
                List<Float> li =new ArrayList<>();
                li.add((float) calculateTime(interview.getTimeSpent())/60);
                int count[] = calculateCount(interview.getCandidateStatus());
                li.add((float) count[0]);
                mapResult.put(interview.getName(),li);
            }

        }
        for (Map.Entry<String,List<Float>> entry: mapResult.entrySet()) {

            String temp="";
            for (Float li : entry.getValue()) {
                temp+=li+",";
            }

            result.put(entry.getKey(),temp.substring(0,temp.length()-1));
        }
        return result;
    }

    public void addInterview(Interview interview) {
        Employee employee = employeeRepository.findByEmployeeId(interview.getEmpId());
        List<Interview> interviews = employee.getInterviews();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        interview.setMonth(months[interview.getDate().getMonth()]);
        interview.setYear(1900+interview.getDate().getYear());
        interviews.add(interview);

        employee.setInterviews(interviews);
        employeeRepository.save(employee);
    }
}
