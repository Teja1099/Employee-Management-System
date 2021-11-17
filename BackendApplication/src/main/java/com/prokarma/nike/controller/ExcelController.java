package com.prokarma.nike.controller;

import com.prokarma.nike.helper.ExcelHelper;
import com.prokarma.nike.message.ResponseMessage;
import com.prokarma.nike.services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin()
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    ExcelService fileService;

    @PostMapping("/upload/employees")
    public ResponseEntity<ResponseMessage> uploadFileEmployees(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {

                    fileService.saveEmployees(file);


                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));

            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping("/upload/leaves")
    public ResponseEntity<ResponseMessage> uploadFileLeaves(@RequestParam("file") MultipartFile file) {
        String message="";
        if(ExcelHelper.hasExcelFormat(file)) {
            try {

                fileService.saveLeaves(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));

            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping("/upload/interviews")
    public ResponseEntity<ResponseMessage> uploadFileInterviews(@RequestParam("file") MultipartFile file) {
        String message="";
        if(ExcelHelper.hasExcelFormat(file)) {
            try {

                fileService.saveInterviews(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));

            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}