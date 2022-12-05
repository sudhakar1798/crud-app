package io.sudhakar.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
public class Employee {
    @JsonProperty("id")
    private long id;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_salary")
    private long employeeSalary;

    @JsonProperty("employee_age")
    private long employeeAge;

    @JsonProperty("profile_image")
    private String profileImage;
}