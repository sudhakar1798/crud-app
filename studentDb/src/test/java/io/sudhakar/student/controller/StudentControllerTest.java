package io.sudhakar.student.controller;

import io.sudhakar.student.dto.Address;
import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.dto.StudentResponse;
import io.sudhakar.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    StudentController studentController;

    @Mock
    StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentService);
    }

    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario SUCCESS
    */
    @Test
    void getAllStudentResponseOk() {

        Address address1 = new Address();
        address1.setArea("btm");
        address1.setId(1);
        address1.setStudentId(1);
        address1.setPincode(560003);
        address1.setDistrict("blr");

        Address address2 = new Address();
        address2.setArea("hsr");
        address2.setId(2);
        address2.setStudentId(2);
        address2.setPincode(333333);
        address2.setDistrict("tvm");

        Student student1 = new Student();
        student1.setId(1);
        student1.setAge(24);
        student1.setName("arr");
        student1.setCourse("java Script");
        student1.setPlace("pune");
        student1.setAddresses(new HashSet<>(Collections.singletonList(address1)));


        Student student2 = new Student();
        student2.setId(2);
        student2.setAge(25);
        student2.setName("gvp");
        student2.setCourse("java");
        student2.setPlace("mumbai");
        student2.setAddresses(new HashSet<>(Collections.singletonList(address2)));

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(students);

        when(studentService.getAllStudent())
                .thenReturn(serviceResponse);


        ResponseEntity<StudentResponse<List<Student>>> responseEntity = studentController.getAllStudent();
        assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(students, (responseEntity.getBody()).getData());

        assertEquals(1, (responseEntity.getBody()).getData().get(0).getId());
        assertEquals("arr", (responseEntity.getBody()).getData().get(0).getName());
        assertEquals("java Script", (responseEntity.getBody()).getData().get(0).getCourse());
        assertEquals("pune", (responseEntity.getBody()).getData().get(0).getPlace());
        assertEquals(24, (responseEntity.getBody()).getData().get(0).getAge());

        assertEquals(2, (responseEntity.getBody()).getData().get(1).getId());
        assertEquals("gvp", (responseEntity.getBody()).getData().get(1).getName());
        assertEquals("java", (responseEntity.getBody()).getData().get(1).getCourse());
        assertEquals("mumbai", (responseEntity.getBody()).getData().get(1).getPlace());
        assertEquals(25, (responseEntity.getBody()).getData().get(1).getAge());

        assertEquals("btm", (responseEntity.getBody()).getData().get(0).getAddresses().stream().findFirst().get().getArea());
        assertEquals(560003, (responseEntity.getBody()).getData().get(0).getAddresses().stream().findFirst().get().getPincode());

        assertEquals("hsr", (responseEntity.getBody()).getData().get(1).getAddresses().stream().findFirst().get().getArea());
        assertEquals(333333, (responseEntity.getBody()).getData().get(1).getAddresses().stream().findFirst().get().getPincode());


    }

    /*
    Given getAllStudents()
    Then  ResponseEntity<StudentResponse<List<Student>>>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getAllStudentResponseInternalServerError() {


        Address address1 = new Address();
        address1.setArea("btm");
        address1.setId(1);
        address1.setStudentId(1);
        address1.setPincode(560003);
        address1.setDistrict("blr");

        Address address2 = new Address();
        address2.setArea("hsr");
        address2.setId(2);
        address2.setStudentId(2);
        address2.setPincode(333333);
        address2.setDistrict("tvm");

        Student student1 = new Student();
        student1.setId(1);
        student1.setAge(24);
        student1.setName("arr");
        student1.setCourse("java Script");
        student1.setPlace("pune");
        student1.setAddresses(new HashSet<>(Collections.singletonList(address1)));

        Student student2 = new Student();
        student2.setId(2);
        student2.setAge(25);
        student2.setName("gvp");
        student2.setCourse("java");
        student2.setPlace("mumbai");
        student2.setAddresses(new HashSet<>(Collections.singletonList(address2)));

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setData(students);

        when(studentService.getAllStudent())
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Student>>> responseEntity = studentController.getAllStudent();
        assertEquals(500, responseEntity.getStatusCodeValue());

        assertEquals(students, (responseEntity.getBody()).getData());
    }

    /*
    Given getStudent()
    Then  ResponseEntity<StudentResponse<Student>>
    Scenario SUCCESS
    */

    @Test
    void getStudentOk() {

        Address address = new Address();
        address.setArea("hsr");
        address.setId(2);
        address.setStudentId(2);
        address.setPincode(333333);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        ServiceResponse<Student> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(student);

        when(studentService.getStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<Student>> responseEntity = studentController.getStudent(1);
        assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(1, responseEntity.getBody().getData().getId());


        assertEquals("blr", (responseEntity.getBody()).getData().getAddresses().stream().findFirst().get().getDistrict());
        assertEquals("hsr", (responseEntity.getBody()).getData().getAddresses().stream().findFirst().get().getArea());


        assertEquals(1, (responseEntity.getBody()).getData().getId());
        assertEquals("arr", (responseEntity.getBody()).getData().getName());
        assertEquals("java Script", (responseEntity.getBody()).getData().getCourse());
        assertEquals("pune", (responseEntity.getBody()).getData().getPlace());
        assertEquals(24, (responseEntity.getBody()).getData().getAge());
    }


    /*
    Given getStudent()
    Then  ResponseEntity<StudentResponse<Student>>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getStudentInternalServerError() {

        Address address = new Address();
        address.setArea("hsr");
        address.setId(2);
        address.setStudentId(2);
        address.setPincode(333333);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        ServiceResponse<Student> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setData(student);

        when(studentService.getStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<Student>> responseEntity = studentController.getStudent(1);
        assertEquals(500, responseEntity.getStatusCodeValue());

    }

    /*
    Given addStudent()
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void addStudentOk() {

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

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(studentService.addStudent(student))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = studentController.addStudent(student);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given addStudent()
    Then  ResponseEntity<Void>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void addStudentInternalServerError() {

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
        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(studentService.addStudent(student))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = studentController.addStudent(student);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }


    /*
    Given updateStudent()
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void updateStudentOk() {

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

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(student);

        when(studentService.updateStudent(1, student))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = studentController.updateStudent(1, student);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }


    /*
    Given updateStudent()
    Then  ResponseEntity<Void>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void updateStudentInternalServerError() {

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

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);


        when(studentService.updateStudent(1, student))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = studentController.updateStudent(1, student);
        assertEquals(500, responseEntity.getStatusCodeValue());

    }


    /*
    Given deleteStudent()
    Then  ResponseEntity<Void>
    Scenario SUCCESS
    */
    @Test
    void deleteStudentOk() {

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

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(studentService.deleteStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = studentController.deleteStudent(1);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteStudent()
    Then  ResponseEntity<Void>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void deleteStudentInternalServerError() {


        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(studentService.deleteStudent(1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = studentController.deleteStudent(1);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given getWithPaginationAndSorting(pageable)
    Then ResponseEntity<StudentResponse<Page<Student>>>
    Scenario SUCCESS
    */
    @Test
    void getWithPaginationAndSortingOk() {

        Address address1 = new Address();
        address1.setArea("btm");
        address1.setId(1);
        address1.setStudentId(1);
        address1.setPincode(560003);
        address1.setDistrict("blr");

        Address address2 = new Address();
        address2.setArea("hsr");
        address2.setId(2);
        address2.setStudentId(2);
        address2.setPincode(333333);
        address2.setDistrict("tvm");


        Student student1 = new Student();
        student1.setId(1);
        student1.setAge(24);
        student1.setName("arr");
        student1.setCourse("java Script");
        student1.setPlace("pune");
        student1.setAddresses(new HashSet<>(Collections.singletonList(address1)));


        Student student2 = new Student();
        student2.setId(2);
        student2.setAge(25);
        student2.setName("gvp");
        student2.setCourse("java");
        student2.setPlace("mumbai");
        student2.setAddresses(new HashSet<>(Collections.singletonList(address2)));

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        Pageable pageable = PageRequest.of(0, 2);

        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());

        ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(studentPage);

        when(studentService.getWithPaginationAndSorting(pageable))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<Page<Student>>> responseEntity = studentController.getWithPaginationAndSorting(pageable);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(2, responseEntity.getBody().getData().getNumberOfElements());

        assertEquals(1, responseEntity.getBody().getData().getContent().get(0).getId());
        assertEquals("arr", responseEntity.getBody().getData().getContent().get(0).getName());
        assertEquals("java Script", responseEntity.getBody().getData().getContent().get(0).getCourse());
        assertEquals("pune", responseEntity.getBody().getData().getContent().get(0).getPlace());
        assertEquals(24, responseEntity.getBody().getData().getContent().get(0).getAge());

        assertEquals(2, responseEntity.getBody().getData().getContent().get(1).getId());
        assertEquals("gvp", responseEntity.getBody().getData().getContent().get(1).getName());
        assertEquals("java", responseEntity.getBody().getData().getContent().get(1).getCourse());
        assertEquals("mumbai", responseEntity.getBody().getData().getContent().get(1).getPlace());
        assertEquals(25, responseEntity.getBody().getData().getContent().get(1).getAge());

        assertEquals("btm", responseEntity.getBody().getData().getContent().get(0).getAddresses().stream().findFirst().get().getArea());
        assertEquals(560003, responseEntity.getBody().getData().getContent().get(0).getAddresses().stream().findFirst().get().getPincode());

        assertEquals("hsr", responseEntity.getBody().getData().getContent().get(1).getAddresses().stream().findFirst().get().getArea());
        assertEquals(333333, responseEntity.getBody().getData().getContent().get(1).getAddresses().stream().findFirst().get().getPincode());


    }


    /*
    Given getWithPaginationAndSorting(pageable)
    Then  ResponseEntity<StudentResponse<Page<Student>>>
    Scenario Error (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getWithPaginationAndSortingInternalServerError() {

        Pageable pageable = PageRequest.of(0, 2);

        when(studentService.getWithPaginationAndSorting(Mockito.any(Pageable.class)))
                .thenThrow(new NoSuchFieldError(""));

        ServiceResponse serviceResponse = new ServiceResponse<>();

        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<StudentResponse<Page<Student>>> responseEntity = studentController.getWithPaginationAndSorting(Mockito.any(Pageable.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

}

