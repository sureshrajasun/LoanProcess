package com.example.workflow.invokeapi;

import com.example.workflow.model.EmploymentDetails;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UpdateDB implements JavaDelegate {
    
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        //getting the Object from camunda process and printing it on console
        Object empJavaObj = execution.getVariable("empJavaObj");
        EmploymentDetails emp = (EmploymentDetails) empJavaObj;
        System.out.println("Emp ID: "+ emp.getEmpId());
        System.out.println("Emp First Name: "+ emp.getEmpFirstName());
        System.out.println("Emp Last Name: "+ emp.getEmpLastName());
        System.out.println("Emp Salary: "+ emp.getEmpSalary());

        //After getting the data from camunda process, invoking API to update DB
        //Able to get the values properly, data can be updated through POST request
        //Sample get request to test API invocation, this server running on 8085 port(Different server)
        final String uri = "http://localhost:8085/api/getMsg";
       // String result = restTemplate.getForObject(uri, String.class);
        System.out.println("API Invoked successfully, Result : ");
    }
}
