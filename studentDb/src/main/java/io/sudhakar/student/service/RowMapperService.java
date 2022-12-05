package io.sudhakar.student.service;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;

import java.util.List;

public interface RowMapperService {
    ServiceResponse<List<Student>> getAllStudents();

    ServiceResponse<List<Student>> getStudent(int id);
}
