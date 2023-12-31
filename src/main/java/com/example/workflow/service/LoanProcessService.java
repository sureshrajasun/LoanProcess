package com.example.workflow.service;

import com.example.workflow.model.Customer;
import com.example.workflow.model.LoanProcessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanProcessService {
    @Autowired
    RuntimeService runtimeService;

    public LoanProcessResponse startProcessByLoanType(Customer customer) throws JsonProcessingException {
        LoanProcessResponse loanProcessResponse = new LoanProcessResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        String customerObj = objectMapper.writeValueAsString(customer);

        ObjectValue empValue = Variables.objectValue(customer)
                .serializationDataFormat("application/json")
                .create();

        String instanceId = runtimeService.createMessageCorrelation("Msg-StartLoanProcess")
                .setVariable("name", customer.getName())
                .setVariable("age", customer.getAge())
                .setVariable("loanType", customer.getLoanType())
                .setVariable("loanAmount", customer.getLoanAmount())
                .setVariable("customerObj", customerObj)
                .setVariable("customerJson", empValue)
                .setVariable("customerJavaObj", customer)
                .correlateStartMessage().getProcessInstanceId();

        loanProcessResponse.setApplicationId(instanceId);
        loanProcessResponse.setMessage("Loan Type Process has been created successfully..");
        loanProcessResponse.setInstanceId(instanceId);
        loanProcessResponse.setStatus(HttpStatus.CREATED);
        return loanProcessResponse;
    }

    public LoanProcessResponse startProcessInstanceByKey(Customer customer) throws JsonProcessingException {
        LoanProcessResponse loanProcessResponse = new LoanProcessResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        String empObject = objectMapper.writeValueAsString(customer);

        String instanceId = runtimeService.startProcessInstanceByKey("Loan_Approval_Process_by_Type", empObject)
                .getProcessInstanceId();

        loanProcessResponse.setApplicationId(instanceId);
        loanProcessResponse.setMessage("Process has been created successfully..");
        loanProcessResponse.setInstanceId(instanceId);
        loanProcessResponse.setStatus(HttpStatus.CREATED);
        return loanProcessResponse;
    }
}
