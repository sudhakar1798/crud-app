package io.sudhakar.student.controller;

import io.sudhakar.student.dto.*;
import io.sudhakar.student.service.RowMapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowMapperControllerTest {

    RowMapperController rowMapperController;

    @Mock
    RowMapperService rowMapperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.rowMapperController = new RowMapperController(rowMapperService);
    }

    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario SUCCESS
    */
    @Test
    void getAllStudentsOK() {
        Address address = new Address();
        address.setArea("btm");
        address.setId(1);
        address.setStudentId(1);
        address.setPincode(560003);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        List<Student> students = new ArrayList<>();
        students.add(student);

        String queryName = "findAll";

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(students);

        Mockito.when(rowMapperService.getAllStudents()).thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = rowMapperController.getAllStudents();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals("arr", (responseEntity.getBody()).getData().get(0).getName());
        assertEquals("pune", (responseEntity.getBody()).getData().get(0).getPlace());

        assertEquals("btm", (responseEntity.getBody()).getData().get(0).getAddresses().stream().findFirst().get().getArea());
        assertEquals(560003, (responseEntity.getBody()).getData().get(0).getAddresses().stream().findFirst().get().getPincode());
    }

    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getAllStudentsInternalServerError() {

        Address address = new Address();
        address.setArea("btm");
        address.setId(1);
        address.setStudentId(1);
        address.setPincode(560003);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        List<Student> students = new ArrayList<>();
        students.add(student);

        String queryName = "findAll";

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setData(students);

        Mockito.when(rowMapperService.getAllStudents())
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = rowMapperController.getAllStudents();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    /*
    Given getStudent()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario SUCCESS
    */
    @Test
    void getStudentsOK() {
        Address address = new Address();
        address.setArea("btm");
        address.setId(1);
        address.setStudentId(1);
        address.setPincode(560003);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        List<Student> students = new ArrayList<>();
        students.add(student);

        String queryName = "findById";

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(students);

        Mockito.when(rowMapperService.getStudent(1)).thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = rowMapperController.getStudent(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals("arr", (responseEntity.getBody()).getData().get(0).getName());
        assertEquals("pune", (responseEntity.getBody()).getData().get(0).getPlace());

        assertEquals("btm", (responseEntity.getBody()).getData().get(0).getAddresses().stream().findFirst().get().getArea());
        assertEquals(560003, (responseEntity.getBody()).getData().get(0).getAddresses().stream().findFirst().get().getPincode());
    }

    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getStudentInternalServerError() {
        Address address = new Address();
        address.setArea("btm");
        address.setId(1);
        address.setStudentId(1);
        address.setPincode(560003);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        List<Student> students = new ArrayList<>();
        students.add(student);

        String queryName = "findAll";

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setData(students);

        Mockito.when(rowMapperService.getAllStudents()).thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = rowMapperController.getAllStudents();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}