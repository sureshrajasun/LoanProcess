package com.example.workflow.controller;

import com.example.workflow.model.Customer;
import com.example.workflow.model.LoanProcessResponse;
import com.example.workflow.service.LoanProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoanProcessController {

    @Autowired
    LoanProcessService loanProcessService;
    @PostMapping("/startLoanProcessByKey")
    public ResponseEntity<LoanProcessResponse> startProcessByKey(@RequestBody Customer customer) throws JsonProcessingException {
        LoanProcessResponse loanProcessResponse = loanProcessService.startProcessInstanceByKey(customer);
        return new ResponseEntity<>(loanProcessResponse,HttpStatus.CREATED);
    }

    @PostMapping("/startLoanProcessByType")
    public ResponseEntity<LoanProcessResponse> startProcessByMessage(@RequestBody Customer customer) throws JsonProcessingException {
        LoanProcessResponse loanProcessResponse = loanProcessService.startProcessByLoanType(customer);
        return new ResponseEntity<>(loanProcessResponse,HttpStatus.CREATED);
    }
}
