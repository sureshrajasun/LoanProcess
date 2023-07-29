package com.example.workflow.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String firstName;
    private String lastName;
    private String city;
    private String country;
}
