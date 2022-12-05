package io.sudhakar.student.service;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    ServiceResponse<List<Student>> getAllStudent();

    ServiceResponse<Student> getStudent(int id);

    ServiceResponse<Void> addStudent(Student student);

    ServiceResponse<Void> updateStudent(int id, Student student);

    ServiceResponse<Void> deleteStudent(int id);

    ServiceResponse<Page<Student>> getWithPaginationAndSorting(Pageable pageable);
}