package io.sudhakar.student.controller;

import io.sudhakar.student.dto.StudentResponse;
import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<StudentResponse<List<Student>>> getAllStudent() {
        log.info("api = /student, method = GET, result = IN_PROGRESS");

        ServiceResponse<List<Student>> serviceResponse = studentService.getAllStudent();

        log.info("api = /student, method = GET, result = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }

    @GetMapping("/{id}")

    public ResponseEntity<StudentResponse<Student>> getStudent(@PathVariable("id") int id) {
        log.info("api = /student{id}, method = GET , result = IN_Progress");

        ServiceResponse<Student> serviceResponse = studentService.getStudent(id);

        log.info("api = /student{id}, method = GET , result = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }

    @PostMapping
    public ResponseEntity<Void> addStudent(@RequestBody Student student) {
        log.info("api = /student, method = POST , result = IN_Progress");

        ServiceResponse<Void> serviceResponse = studentService.addStudent(student);

        log.info("api = /student, method = POST , result = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        log.info("api = /student{id}, method = PUT , result = IN_Progress");

        ServiceResponse<Void> serviceResponse = studentService.updateStudent(id, student);

        log.info("api = /student{id},method = PUT, result = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
        log.info("api = /student{id}, method = DELETE , result = IN_Progress");

        ServiceResponse<Void> serviceResponse = studentService.deleteStudent(id);
        log.info("api = /student{id},method = DELETE, result = SUCCESS");

        return ResponseEntity.status(serviceResponse.getHttpStatus()).build();
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<StudentResponse<Page<Student>>> getWithPaginationAndSorting(@PageableDefault(size = 25, sort = "id", direction = Sort.Direction.ASC, page = 0) Pageable pageable) {
        log.info("api = /pagination, method = POST, result = IN_PROGRESS");

        ServiceResponse<Page<Student>> serviceResponse = studentService.getWithPaginationAndSorting(pageable);

        log.info("api = /pagination, method = POST, result = SUCCESS");

        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));

    }

}