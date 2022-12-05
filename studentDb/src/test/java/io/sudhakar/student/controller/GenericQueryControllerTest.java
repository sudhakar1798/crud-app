package io.sudhakar.student.controller;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.dto.StudentResponse;
import io.sudhakar.student.service.GenericService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class GenericQueryControllerTest {

    GenericQueryController genericQueryController;

    @Mock
    GenericService genericService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genericQueryController = new GenericQueryController(genericService);
    }


    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario SUCCESS
    */

    @Test
    void getAllStudentsOk() {

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        List<Student> students = new ArrayList<>();
        students.add(student);

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(students);

        when(genericService.getAllStudents())
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = genericQueryController.getAllStudents();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(1, (responseEntity.getBody()).getData().get(0).getId());
        assertEquals("arr", (responseEntity.getBody()).getData().get(0).getName());
        assertEquals("java Script", (responseEntity.getBody()).getData().get(0).getCourse());
        assertEquals("pune", (responseEntity.getBody()).getData().get(0).getPlace());
    }

    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario INTERNAL_SERVER_ERROR
    */
    @Test
    void getAllStudentsInternalServerError() {

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(genericService.getAllStudents())
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = genericQueryController.getAllStudents();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    /*
    Given addStudent(student)
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void addStudentOk() {

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        Mockito.when(genericService.addStudent(student))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.addStudent(student);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given addStudent(student)
    Then  ResponseEntity<Void>
    Scenario INTERNAL_SERVER_ERROR
    */

    @Test
    void addStudentInternalServerError() {

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(genericService.addStudent(Mockito.any()))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.addStudent(Mockito.any());
        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    /*
    Given updateStudent(student)
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void updateStudentOk() {
        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(student);

        Mockito.when(genericService.updateStudent(student, 1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.updateStudent(student, 1);

        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    /*
    Given updateStudent(student)
    Then  ResponseEntity<Void>
    Scenario INTERNAL_SERVER_ERROR
    */
    @Test
    void updateStudentInternalServerError() {

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(genericService.updateStudent(Mockito.any(), Mockito.any()))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.updateStudent(Mockito.any(), Mockito.any());

        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteStudent(id)
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void deleteStudentOk() {

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(genericService.deleteStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.deleteStudent(1);
        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    /*
    Given deleteStudent(id)
    Then  ResponseEntity<Void>
    Scenario INTERNAL_SERVER_ERROR
    */
    @Test
    void deleteStudentInternalServerError() {

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(genericService.deleteStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.deleteStudent(1);
        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    /*
    Given getStudent(id)
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario SUCCESS
    */

    @Test
    void getStudentOk() {

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        List<Student> students = new ArrayList<>();
        students.add(student);

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(students);

        when(genericService.getStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = genericQueryController.getStudent(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(1, (responseEntity.getBody()).getData().get(0).getId());
        assertEquals("arr", (responseEntity.getBody()).getData().get(0).getName());
        assertEquals("java Script", (responseEntity.getBody()).getData().get(0).getCourse());
        assertEquals("pune", (responseEntity.getBody()).getData().get(0).getPlace());

    }

    /*
    Given getStudent(id)
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario INTERNAL_SERVER_ERROR
     */

    @Test
    void getStudentInternalServerError() {

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(genericService.getStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = genericQueryController.getStudent(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    /*
    Given deleteMany(ids)
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void deleteManyOk() {

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        Mockito.doReturn(serviceResponse).when(genericService).deleteMany(ids);

        ResponseEntity<Void> responseEntity = genericQueryController.deleteMany(ids);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteMany(ids)
    Then  ResponseEntity<Void>
    Scenario INTERNAL_SERVER_ERROR
    */
    @Test
    void deleteManyInternalServerError() {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.doReturn(serviceResponse).when(genericService).deleteMany(Mockito.any());

        ResponseEntity<Void> responseEntity = genericQueryController.deleteMany(Mockito.any());
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given updateMany(students,ids)
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void updateManyOk() {

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        Mockito.doReturn(serviceResponse).when(genericService).updateMany(ids, student);

        ResponseEntity<Void> responseEntity = genericQueryController.updateMany(ids, student);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given updateMany(students,ids)
    Then  ResponseEntity<Void>
    Scenario INTERNAL_SERVER_ERROR
    */
    @Test
    void updateManyInternalServerError() {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.doReturn(serviceResponse).when(genericService).updateMany(Mockito.any(), Mockito.any());

        ResponseEntity<Void> responseEntity = genericQueryController.updateMany(Mockito.any(), Mockito.any());
        assertEquals(500, responseEntity.getStatusCodeValue());
    }


    /*
    Given addMany(students)
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void addManyOk() {

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");

        List<Student> students = new ArrayList<>();
        students.add(student);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        Mockito.doReturn(serviceResponse).when(genericService).addMany(students);

        ResponseEntity<Void> responseEntity = genericQueryController.addMany(students);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /*
    Given addMany(students)
    Then  ResponseEntity<Void>
    Scenario INTERNAL_SERVER_ERROR
    */

    @Test
    void addManyInternalServerError() {

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(genericService.addMany(Mockito.any()))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = genericQueryController.addMany(Mockito.any());
        assertEquals(500, responseEntity.getStatusCodeValue());

    }

}