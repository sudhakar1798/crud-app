package io.sudhakar.student.controller;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.dto.StudentResponse;
import io.sudhakar.student.service.RowMapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/rowMapper")
public class RowMapperController {

    private final RowMapperService rowMapperService;

    public RowMapperController(RowMapperService rowMapperService) {
        this.rowMapperService = rowMapperService;
    }

    @GetMapping
    public ResponseEntity<StudentResponse<List<Student>>> getAllStudents() {
        log.info("api = /rowMapper, method = GET, result = IN_PROGRESS");

        ServiceResponse<List<Student>> serviceResponse = rowMapperService.getAllStudents();

        log.info("api = /rowMapper, method = GET, result = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }

    @GetMapping("/id")
    public ResponseEntity<StudentResponse<List<Student>>> getStudent(@RequestParam("id") int id) {
        log.info("api = /rowMapper/{id}, method = GET, result = IN_PROGRESS");

        ServiceResponse<List<Student>> serviceResponse = rowMapperService.getStudent(id);

        log.info("api = /rowMapper/{id}, method = GET, result = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }
}
