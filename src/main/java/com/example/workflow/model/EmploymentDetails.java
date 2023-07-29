package com.example.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDetails {
    private String empId;
    private String empFirstName;
    private String empLastName;
    private Long empSalary;
    private String empDesignation;
    private Address address;
}
