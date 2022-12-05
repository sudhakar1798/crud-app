package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.entity.AddressEntity;
import io.sudhakar.student.entity.StudentEntity;
import io.sudhakar.student.repository.RowMapperRepository;
import io.sudhakar.student.repository.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RowMapperServiceImplTest {

    RowMapperServiceImpl rowMapperService;

    @Mock
    RowMapperRepository rowMapperRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rowMapperService = new RowMapperServiceImpl(rowMapperRepository);
    }

    /*
    Given executeGetAllQuery(),getAllStudents()
    Then StudentEntities With their AddressEntities ,ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void getAllStudentsOk() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setArea("btm");
        addressEntity.setId(1);
        addressEntity.setStudentId(1);
        addressEntity.setPincode(560003);
        addressEntity.setDistrict("blr");

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1);
        studentEntity.setAge(24);
        studentEntity.setName("arr");
        studentEntity.setCourse("java Script");
        studentEntity.setPlace("pune");
        studentEntity.setAddressEntitiesList(new HashSet<>(Collections.singletonList(addressEntity)));

        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity);

        String queryName = "findAll";

        Mockito.doReturn(studentEntities)
                .when(rowMapperRepository).executeGetAllQuery(null, queryName, new StudentMapper());

        ServiceResponse<List<Student>> serviceResponse = rowMapperService.getAllStudents();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

    }

    /*
    Given executeGetAllQuery(),getAllStudents()
    Then StudentEntities With their AddressEntities ,ServiceResponse
    Scenario INTERNAL_SERVER_ERROR
    */
    @Test
    void getAllStudentsInternalServerError() {

        String queryName = "findAll";

        Mockito.doThrow(new NullPointerException(""))
                .when(rowMapperRepository).executeGetAllQuery(null, queryName, new StudentMapper());

        ServiceResponse<List<Student>> serviceResponse = rowMapperService.getAllStudents();

        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given executeGetQuery(),getStudent()
    Then StudentEntities With their AddressEntities ,ServiceResponse
    Scenario SUCCESS
     */
    @Test
    void getStudentOk() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setArea("btm");
        addressEntity.setId(1);
        addressEntity.setStudentId(1);
        addressEntity.setPincode(560003);
        addressEntity.setDistrict("blr");

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1);
        studentEntity.setAge(24);
        studentEntity.setName("arr");
        studentEntity.setCourse("java Script");
        studentEntity.setPlace("pune");
        studentEntity.setAddressEntitiesList(new HashSet<>(Collections.singletonList(addressEntity)));

        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity);

        String queryName = "findById";

        Mockito.doReturn(studentEntities)
                .when(rowMapperRepository).executeGetQuery(1, queryName, new StudentMapper());

        ServiceResponse<List<Student>> serviceResponse = rowMapperService.getStudent(1);

        serviceResponse.setHttpStatus(HttpStatus.OK);
        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given executeGetQuery(),getStudent()
    Then StudentEntities With their AddressEntities ,ServiceResponse
    Scenario INTERNAL_SERVER_ERROR
     */
    @Test
    void getStudentInternalServerError() {

        String queryName = "findById";

        Mockito.doThrow(new NullPointerException(""))
                .when(rowMapperRepository).executeGetQuery(1, queryName, new StudentMapper());

        ServiceResponse<List<Student>> serviceResponse = rowMapperService.getStudent(1);

        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

}