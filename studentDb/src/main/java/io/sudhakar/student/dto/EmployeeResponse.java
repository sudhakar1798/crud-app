package io.sudhakar.student.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EmployeeResponse {
    private String status;
    private Employee data;
    private String message;
}
