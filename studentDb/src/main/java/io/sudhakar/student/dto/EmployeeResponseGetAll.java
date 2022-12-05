package io.sudhakar.student.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
public class EmployeeResponseGetAll {
    private String status;
    private List<Employee> data;
    private String message;
}
