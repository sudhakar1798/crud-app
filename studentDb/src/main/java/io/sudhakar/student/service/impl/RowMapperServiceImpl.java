package io.sudhakar.student.service.impl;


import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.repository.RowMapperRepository;
import io.sudhakar.student.repository.mapper.StudentMapper;
import io.sudhakar.student.service.RowMapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RowMapperServiceImpl implements RowMapperService {

    @Autowired
    private final RowMapperRepository rowMapperRepository;

    public RowMapperServiceImpl(RowMapperRepository rowMapperRepository) {
        this.rowMapperRepository = rowMapperRepository;
    }

    public ServiceResponse<List<Student>> getAllStudents() {
        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = getAllStudents, status = IN_PROGRESS");
            String queryName = "findAll";
            List<Student> students = rowMapperRepository.executeGetAllQuery(null, queryName, new StudentMapper());

            log.info("operation = getAllStudents, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(students);

            return serviceResponse;

        } catch (Exception e) {
            log.error("operation = getAllStudents, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }


    public ServiceResponse<List<Student>> getStudent(int id) {
        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = getStudent, status = IN_PROGRESS");
            String queryName = "findById";
            List<Student> students = rowMapperRepository.executeGetQuery(id, queryName, new StudentMapper());

            log.info("operation = getStudent, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(students);

            return serviceResponse;

        } catch (Exception e) {
            log.error("operation = getStudent, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

}
