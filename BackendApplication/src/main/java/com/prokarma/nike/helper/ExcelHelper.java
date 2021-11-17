package com.prokarma.nike.helper;


import com.prokarma.nike.entity.*;
import com.prokarma.nike.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class ExcelHelper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  EmployeeRepository employeeRepository;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public  List<Employee> excelToEmployees(InputStream is) {

        try {
            Workbook workbook = new XSSFWorkbook(is);
            //Sheet sheet = workbook.getSheet(SHEET);
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            Iterator<Row> rows= sheet.iterator();
            List<Employee> employees = new ArrayList<Employee>();

            int rowNumber = 0;
            int counter=0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if(isEmptyRow(currentRow)) continue;
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Employee employee = new Employee();

                int cellIdx = 0;
                System.out.println("req"+counter);
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            break;
                        case 1:
                            System.out.println(currentCell.getNumericCellValue());
                            employee.setEmpId((int) currentCell.getNumericCellValue());
                            break;
                        case 2:
                            System.out.println(cellIdx);
                            employee.setPkEmail(currentCell.getStringCellValue());
                            break;
                        case 3:
                            System.out.println(cellIdx);
                            employee.setName(currentCell.getStringCellValue());
                            break;
                        case 4:
                            System.out.println(cellIdx);
                            employee.setStatus(currentCell.getStringCellValue());
                            break;
                        case 5:
                            System.out.println(cellIdx);
                            employee.setContact((long) currentCell.getNumericCellValue());
                            break;
                        case 6:
                            System.out.println(cellIdx);
                            employee.setBaseLocation(currentCell.getStringCellValue());
                            break;
                        case 7:
                            System.out.println(cellIdx);
                            employee.setRole(currentCell.getStringCellValue());
                            break;
                        case 8:
                            System.out.println(cellIdx);
                            employee.setProject(currentCell.getStringCellValue());
                            break;
                        case 9:
                            System.out.println(cellIdx);
                            employee.setProjectCode(currentCell.getStringCellValue());
                            break;
                        case 10:
                            System.out.println(cellIdx);
                            employee.setDesignation(currentCell.getStringCellValue());
                            break;
                        case 11:
                            System.out.println(cellIdx);
                            employee.setHasH1(currentCell.getStringCellValue());
                            break;
                        case 12:
                            System.out.println(cellIdx);
                            employee.setTotalExperienceInIT(currentCell.getStringCellValue());
                            break;

                        case 13:System.out.println(cellIdx);
                            employee.setTotalExperienceInPK(currentCell.getStringCellValue());
                            break;
                        case 14:System.out.println(cellIdx);
                            employee.setTotalExperienceInNIKE(currentCell.getStringCellValue());
                            break;
                        case 15:System.out.println(cellIdx);
                            employee.setPrimarySkills(currentCell.getStringCellValue());
                            break;
                        case 16:System.out.println(cellIdx);
                            employee.setSceondarySkills(currentCell.getStringCellValue());
                            break;
//                        case 17:
//                            String sDate1="31/12/1998";
//                            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
//                            employee.setDOB(currentCell.getDateCellValue());
//                            break;
//                        case 18:
//                            employee.setJoiningDate(currentCell.getDateCellValue());
//                            break;
                        case 19:
                            System.out.println(cellIdx);
                            employee.setAddress(currentCell.getStringCellValue());
                            break;
                        case 20:
                            try{
                            System.out.println(cellIdx);
                            String[] str =  currentCell.getStringCellValue().split("[/]+");

                            EmergencyContact emergencyContact = new EmergencyContact();
                            emergencyContact.setName(str[0]);
                            emergencyContact.setContact(Long.parseLong(str[1]));
                            employee.setEmergencyContact(emergencyContact);}
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case 21:
                            System.out.println(cellIdx);
                            employee.setCertification(currentCell.getStringCellValue());
                            break;
                        case 22:
                            System.out.println(cellIdx);
                            employee.setStatus2(currentCell.getStringCellValue());
                            break;
                        case 23:
                            System.out.println(cellIdx);
                            employee.setNikeId(currentCell.getStringCellValue());
                            break;
                        case 24:
                            System.out.println(cellIdx);
                            employee.setNikeEmail(currentCell.getStringCellValue());
                            break;
                            case 25:
                                System.out.println(cellIdx);
                                employee.setComments(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                counter++;
                employee.setPassword(passwordEncoder.encode( "user@123"));
                List<Authority> authorityList = new ArrayList<>();
                authorityList.add(createAuthority(employee.getEmpId(), "USER","User role"));
                employee.setAuthorities(authorityList);
                employees.add(employee);

            }
            workbook.close();
            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static Authority createAuthority(Integer empId, String roleCode, String roleDescription) {
        Authority authority=new Authority();
        authority.setEmpId(empId);
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }

    public   void excelToLeaves(InputStream is) {
        try {
            List<Leave> leaves = new ArrayList<>();
            String[] months={"August","September"};
            Workbook workbook = new XSSFWorkbook(is);
            //Sheet sheet = workbook.getSheet(SHEET);
            int t = workbook.getNumberOfSheets();

            for(int i=0;i< 2;i++) {
                XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(i);
                Iterator<Row> rows= sheet.iterator();
                boolean isFirstRow = true;
                System.out.println("sheet"+i);
                int count=0;
                while (rows.hasNext()) {

                    System.out.println("row"+count);
                    Row currentRow = rows.next();
                    if(isEmptyRow(currentRow)) continue;
                    if(isFirstRow) {
                        isFirstRow=!isFirstRow;
                        continue;
                    }
                    Iterator<Cell> cellInRow = currentRow.iterator();
                    Leave leave = new Leave();
                    int cellIdx =0;
                    while (cellInRow.hasNext()) {
                        Cell currentCell = cellInRow.next();

                        switch (cellIdx) {
                            case 0:
                                System.out.println(cellIdx);
                                //Integer integer=new Integer();
                                leave.setEmpId(new Integer((int) currentCell.getNumericCellValue()));
                                break;
                            case 1:
                                System.out.println(cellIdx);
                                leave.setPkEmail(currentCell.getStringCellValue());
                                break;
                            case 2:
                                System.out.println(cellIdx);
                                leave.setName(currentCell.getStringCellValue());
                                break;
                            case 3:
                                System.out.println(cellIdx);
                                leave.setProjectCode(currentCell.getStringCellValue());
                                break;
                            case 4:
                                System.out.println(currentCell.getCellType());
                                if(currentCell.getCellType().equals(CellType.NUMERIC)) {
                                    String str = new String(String.valueOf(currentCell.getNumericCellValue()));
                                    leave.setDays(str.substring(0,str.length()-2));
                                }
                                else
                                    leave.setDays(currentCell.getStringCellValue());
                                System.out.println(cellIdx);
                                break;
                            default:
                                break;
                        }
                        cellIdx++;
                        leave.setMonth(months[i]);
                        leave.setYear(2019);
                    }

                    Employee employee = employeeRepository.findByEmployeeId(leave.getEmpId());
                    List<Leave> l = employee.getLeaves();
                    l.add(leave);
                    employee.setLeaves(l);
                    employeeRepository.save(employee);
//                    leaves.add(leave);
                    count++;
                }
            }
            workbook.close();

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public   void excelToInterview(InputStream is) {
        try {
            List<Interview> interviews = new ArrayList<>();
            String[] months={"August","September","October"};

            Workbook workbook = new XSSFWorkbook(is);
            //Sheet sheet = workbook.getSheet(SHEET);
            int t = workbook.getNumberOfSheets();
            for (int i=0; i<t;i++) {
                XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(i);
                Iterator<Row> rows= sheet.iterator();
                boolean isFirstRow = true;
                System.out.println("sheet"+i);
                int count=0;
                while (rows.hasNext()) {
                    System.out.println("Row"+(count+1));

                    Row currentRow = rows.next();
                    if(isEmptyRow(currentRow)) continue;
                    if(isFirstRow) {
                        isFirstRow=!isFirstRow;
                        continue;
                    }
                    Iterator<Cell> cellInRow = currentRow.iterator();
                    Interview interview = new Interview();
                    int cellIdx =0;

                    while (cellInRow.hasNext()) {
                        Cell currentCell = cellInRow.next();

                        switch (cellIdx) {
                            case 0:
                                System.out.println(cellIdx);
                                interview.setEmpId(new Integer((int) currentCell.getNumericCellValue()));
                                break;
                            case 1:
                                System.out.println(cellIdx);
                                interview.setName(currentCell.getStringCellValue());
                                break;
                            case 2:
                                System.out.println(cellIdx);
                                interview.setInterviewType(currentCell.getStringCellValue());
                                break;

                            case 3:
                                System.out.println(cellIdx);
                                interview.setRound(currentCell.getStringCellValue());
                                break;
                            case 4:
                                System.out.println(cellIdx);
                                interview.setTechnologyStack(currentCell.getStringCellValue());
                                break;
                            case 5:
                                System.out.println(currentCell.getCellType());
                                interview.setDate(currentCell.getDateCellValue());
                                break;
                            case 6:
                                System.out.println(cellIdx);
                                interview.setTimeSpent(currentCell.getStringCellValue());
                                break;
                            case 7:
                                System.out.println(cellIdx);
                                interview.setCandidateStatus(currentCell.getStringCellValue());
                                break;
                            case 8:
                                System.out.println(cellIdx);
                                interview.setComments(currentCell.toString());
                                break;
                            default:
                                break;
                        }
                        cellIdx++;
                        interview.setMonth(months[i]);
                        interview.setYear(2019);
                    }
                    Employee employee = employeeRepository.findByEmployeeId(interview.getEmpId());
                    List<Interview> l = employee.getInterviews();
                    l.add(interview);
                    employee.setInterviews(l);
                    employeeRepository.save(employee);
//                    interviews.add(interview);
                }
            }
            workbook.close();

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static boolean isEmptyRow(Row row) {
        boolean isEmpty = true;
        DataFormatter dataFormatter = new DataFormatter();
        if (row != null) {
            for (Cell cell : row) {
                if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }
}