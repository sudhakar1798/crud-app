package io.sudhakar.student.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServiceResponse<T> {
    private T data;
    private HttpStatus httpStatus;
}
