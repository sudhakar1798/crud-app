package io.sudhakar.student.controller;

import io.sudhakar.student.dto.*;
import io.sudhakar.student.service.impl.RestTemplateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/employee")
public class RestTemplateController {
    private final RestTemplateServiceImpl restTemplateService;

    public RestTemplateController(RestTemplateServiceImpl restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping
    public ResponseEntity<StudentResponse<EmployeeResponseGetAll>> getAllEmployee() {

        log.info("api = /employee, method = GET, status = IN_PROCESS");

        ServiceResponse<EmployeeResponseGetAll> serviceResponse = restTemplateService.getAllEmployee();

        log.info("api = /employee, method = GET, status = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse<EmployeeResponse>> getEmployeeById(@PathVariable("id") int id) {

        log.info("api = /employee, method = GET, status = IN_PROCESS");

        ServiceResponse<EmployeeResponse> serviceResponse = restTemplateService.getEmployeeById(id);

        log.info("api = /employee, method = GET, status = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }

    @PostMapping
    public ResponseEntity<StudentResponse<EmployeeResponse>> addEmployee(@RequestBody Employee employee) {

        log.info("api = /employee, method = POST, status = IN_PROCESS");

        ServiceResponse<EmployeeResponse> serviceResponse = restTemplateService.addEmployee(employee);

        log.info("api = /employee, method = POST, status = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(new StudentResponse<>(serviceResponse.getData()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse<Void>> updateEmployee(@RequestBody Employee employee,@PathVariable("id") int id) {

        log.info("api = /employee, method = PUT, status = IN_PROCESS");

        ServiceResponse<Void> serviceResponse = restTemplateService.updateEmployee(employee,id);

        log.info("api = /employee, method = PUT, status = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<StudentResponse<Void>> deleteEmployee(@PathVariable("id") int id) {

        log.info("api = /employee, method = DELETE, status = IN_PROCESS");

        ServiceResponse<Void> serviceResponse = restTemplateService.deleteEmployee(id);

        log.info("api = /employee, method = DELETE, status = SUCCESS");
        return ResponseEntity.status(serviceResponse.getHttpStatus()).build();
    }

}
