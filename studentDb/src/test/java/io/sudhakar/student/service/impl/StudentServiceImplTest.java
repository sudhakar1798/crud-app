package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.Address;
import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.entity.AddressEntity;
import io.sudhakar.student.entity.StudentEntity;
import io.sudhakar.student.repository.StudentRepository;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class StudentServiceImplTest {

    StudentServiceImpl studentService;

    @Mock
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentRepository);
    }

    /*
    Given findAll(),getAllStudents()
    Then StudentEntities With their AddressEntities ,ServiceResponse
    Scenario SUCCESS
     */
    @Test
    void getAllStudentOk() {

        AddressEntity addressEntity1 = new AddressEntity();
        addressEntity1.setArea("btm");
        addressEntity1.setId(1);
        addressEntity1.setStudentId(1);
        addressEntity1.setPincode(560003);
        addressEntity1.setDistrict("blr");

        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setArea("hsr");
        addressEntity2.setId(2);
        addressEntity2.setStudentId(2);
        addressEntity2.setPincode(333333);
        addressEntity2.setDistrict("tvm");

        StudentEntity studentEntity1 = new StudentEntity();
        studentEntity1.setId(1);
        studentEntity1.setAge(24);
        studentEntity1.setName("arr");
        studentEntity1.setCourse("java Script");
        studentEntity1.setPlace("pune");
        studentEntity1.setAddressEntitiesList(new HashSet<>(Collections.singletonList(addressEntity1)));

        StudentEntity studentEntity2 = new StudentEntity();
        studentEntity2.setId(2);
        studentEntity2.setAge(25);
        studentEntity2.setName("gvp");
        studentEntity2.setCourse("java");
        studentEntity2.setPlace("mumbai");
        studentEntity2.setAddressEntitiesList(new HashSet<>(Collections.singletonList(addressEntity2)));

        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity1);
        studentEntities.add(studentEntity2);


        Mockito.when(studentRepository.findAll())
                .thenReturn(studentEntities);

        ServiceResponse<List<Student>> serviceResponse = studentService.getAllStudent();

        serviceResponse.setHttpStatus(HttpStatus.OK);
        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

        assertEquals(2, serviceResponse.getData().size());

        assertEquals(2, serviceResponse.getData().get(1).getId());
        assertEquals(25, serviceResponse.getData().get(1).getAge());
        assertEquals("mumbai", serviceResponse.getData().get(1).getPlace());
        assertEquals("gvp", serviceResponse.getData().get(1).getName());
        assertEquals("java", serviceResponse.getData().get(1).getCourse());

        assertEquals(1, serviceResponse.getData().get(0).getId());
        assertEquals(24, serviceResponse.getData().get(0).getAge());
        assertEquals("pune", serviceResponse.getData().get(0).getPlace());
        assertEquals("arr", serviceResponse.getData().get(0).getName());
        assertEquals("java Script", serviceResponse.getData().get(0).getCourse());

        assertEquals("btm", serviceResponse.getData().get(0).getAddresses().stream().findFirst().get().getArea());
        assertEquals(560003, serviceResponse.getData().get(0).getAddresses().stream().findFirst().get().getPincode());

        assertEquals("hsr", serviceResponse.getData().get(1).getAddresses().stream().findFirst().get().getArea());
        assertEquals(333333, serviceResponse.getData().get(1).getAddresses().stream().findFirst().get().getPincode());

    }

    /*
    Given findAll(),getAllStudents()
    Then StudentEntities With their AddressEntities ,ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getAllStudentInternalServerError() {

        Mockito.when(studentRepository.findAll())
                .thenThrow(new NullPointerException(""));

        ServiceResponse<List<Student>> serviceResponse = studentService.getAllStudent();

        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());

    }

    /*
    Given findById(),getStudent()
    Then StudentEntity With their AddressEntities ,ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void getStudentOK() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1);
        studentEntity.setAge(24);
        studentEntity.setName("arr");
        studentEntity.setCourse("java Script");
        studentEntity.setPlace("pune");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setArea("hsr");
        addressEntity.setId(2);
        addressEntity.setStudentId(1);
        addressEntity.setPincode(333333);
        addressEntity.setDistrict("blr");

        Set<AddressEntity> addressEntities = new HashSet<>();
        addressEntities.add(addressEntity);

        studentEntity.setAddressEntitiesList(addressEntities);


        Mockito.when(studentRepository.findById(1))
                .thenReturn(Optional.of(studentEntity));

        ServiceResponse<Student> serviceResponse = studentService.getStudent(1);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

        assertEquals("blr", serviceResponse.getData().getAddresses().stream().findFirst().get().getDistrict());
        assertEquals("hsr", serviceResponse.getData().getAddresses().stream().findFirst().get().getArea());


        assertEquals(1, serviceResponse.getData().getId());
        assertEquals(24, serviceResponse.getData().getAge());
        assertEquals("arr", serviceResponse.getData().getName());
        assertEquals("java Script", serviceResponse.getData().getCourse());
        assertEquals("pune", serviceResponse.getData().getPlace());

    }


    /*
    Given findById(),getStudent()
    Then StudentEntity With their AddressEntities ,ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getStudentInternalServerError() {

        Mockito.when(studentRepository.findById(any()))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Student> serviceResponse = studentService.getStudent(1);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given save(),addStudent()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void addStudentOK() {

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

        Mockito.when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(null);

        ServiceResponse<Void> serviceResponse = studentService.addStudent(student);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

    }

    /*
   Given save(),addStudent()
   Then  ServiceResponse
   Scenario ERROR (INTERNAL_SERVER_ERROR)
   */
    @Test
    void addStudentInternalServerError() {
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

        Mockito.when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Void> serviceResponse = studentService.addStudent(student);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());

    }

    /*
    Given save(),updateStudent()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void updateStudentOk() {
        StudentEntity student1 = new StudentEntity();
        student1.setId(5);
        student1.setAge(24);
        student1.setName("arr");
        student1.setCourse("java Script");
        student1.setPlace("pune");

        Address address = new Address();
        address.setArea("hsr");
        address.setId(5);
        address.setStudentId(2);
        address.setPincode(333333);
        address.setDistrict("blr");

        Student student = new Student();
        student.setId(5);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(address)));

        Optional<StudentEntity> studentEntity = studentRepository.findById(5);
        Mockito.when(studentEntity)
                .thenReturn(Optional.of(student1));

        Mockito.when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(null);

        ServiceResponse<Void> serviceResponse = studentService.updateStudent(5, student);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given save(),updateStudent()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void updateStudentInternalServerError() {

        Address addressEntity = new Address();
        addressEntity.setArea("btm");
        addressEntity.setId(1);
        addressEntity.setStudentId(1);
        addressEntity.setPincode(560003);
        addressEntity.setDistrict("blr");

        Student student = new Student();
        student.setId(1);
        student.setAge(24);
        student.setName("arr");
        student.setCourse("java Script");
        student.setPlace("pune");
        student.setAddresses(new HashSet<>(Collections.singletonList(addressEntity)));

        Mockito.when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Void> serviceResponse = studentService.updateStudent(1, student);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());

    }


    /*
    Given deleteById()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void deleteStudentOK() {

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setArea("btm");
        addressEntity.setId(1);
        addressEntity.setStudentId(1);
        addressEntity.setPincode(560003);
        addressEntity.setDistrict("blr");

        Student studentEntity = new Student();
        studentEntity.setId(1);
        studentEntity.setAge(24);
        studentEntity.setName("arr");
        studentEntity.setCourse("java Script");
        studentEntity.setPlace("pune");
        studentEntity.setAddresses(new HashSet(Collections.singletonList(addressEntity)));

        Mockito.doNothing().when(studentRepository).deleteById(1);

        ServiceResponse<Void> serviceResponse = studentService.deleteStudent(1);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

        verify(studentRepository, times(1)).deleteById(1);
    }


    /*
    Given deleteById(),deleteStudent()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void deleteStudentInternalServerError() {

        doThrow(new NullPointerException("")).when(studentRepository).deleteById(1);

        ServiceResponse<Void> serviceResponse = studentService.deleteStudent(1);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
   Given findAll(pageable),getWithPaginationAndSorting(pageable)
   Then  Page<StudentEntity> ,ServiceResponse
   Scenario SUCCESS
    */
    @Test
    void getWithPaginationAndSortingOk() {


        AddressEntity addressEntity1 = new AddressEntity();
        addressEntity1.setArea("btm");
        addressEntity1.setId(1);
        addressEntity1.setStudentId(1);
        addressEntity1.setPincode(560003);
        addressEntity1.setDistrict("blr");

        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setArea("hsr");
        addressEntity2.setId(2);
        addressEntity2.setStudentId(2);
        addressEntity2.setPincode(333333);
        addressEntity2.setDistrict("tvm");

        StudentEntity studentEntity1 = new StudentEntity();
        studentEntity1.setId(1);
        studentEntity1.setAge(24);
        studentEntity1.setName("arr");
        studentEntity1.setCourse("java Script");
        studentEntity1.setPlace("pune");
        studentEntity1.setAddressEntitiesList(new HashSet<>(Collections.singletonList(addressEntity1)));

        StudentEntity studentEntity2 = new StudentEntity();
        studentEntity2.setId(2);
        studentEntity2.setAge(25);
        studentEntity2.setName("gvp");
        studentEntity2.setCourse("java");
        studentEntity2.setPlace("mumbai");
        studentEntity2.setAddressEntitiesList(new HashSet<>(Collections.singletonList(addressEntity2)));

        List<StudentEntity> students = new ArrayList<>();
        students.add(studentEntity1);
        students.add(studentEntity2);

        Pageable pageable = PageRequest.of(0, 2);

        Page<StudentEntity> studentPage = new PageImpl<>(students, pageable, students.size());

        when(studentRepository.findAll(pageable))
                .thenReturn(studentPage);

        ServiceResponse<Page<Student>> serviceResponse = studentService.getWithPaginationAndSorting(pageable);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

        assertEquals(2, serviceResponse.getData().getTotalElements());

        assertEquals(1, serviceResponse.getData().getContent().get(0).getId());
        assertEquals("arr", serviceResponse.getData().getContent().get(0).getName());
        assertEquals("java Script", serviceResponse.getData().getContent().get(0).getCourse());
        assertEquals("pune", serviceResponse.getData().getContent().get(0).getPlace());
        assertEquals(24, serviceResponse.getData().getContent().get(0).getAge());


        assertEquals(2, serviceResponse.getData().getContent().get(1).getId());
        assertEquals("gvp", serviceResponse.getData().getContent().get(1).getName());
        assertEquals("java", serviceResponse.getData().getContent().get(1).getCourse());
        assertEquals("mumbai", serviceResponse.getData().getContent().get(1).getPlace());
        assertEquals(25, serviceResponse.getData().getContent().get(1).getAge());

        assertEquals("btm", serviceResponse.getData().getContent().get(0).getAddresses().stream().findFirst().get().getArea());
        assertEquals(560003, serviceResponse.getData().getContent().get(0).getAddresses().stream().findFirst().get().getPincode());

        assertEquals("hsr", serviceResponse.getData().getContent().get(1).getAddresses().stream().findFirst().get().getArea());
        assertEquals(333333, serviceResponse.getData().getContent().get(1).getAddresses().stream().findFirst().get().getPincode());


    }

    /*
    Given findAll(pageable),getWithPaginationAndSorting(pageable)
    Then  Page<StudentEntity> ,ServiceResponse
    Scenario Error (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getWithPaginationAndSortingInternalServerError() {

        Pageable pageable = PageRequest.of(0, 2);

        when(studentRepository.findAll(Mockito.any(Pageable.class)))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Page<Student>> serviceResponse = studentService.getWithPaginationAndSorting(pageable);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());

    }

}