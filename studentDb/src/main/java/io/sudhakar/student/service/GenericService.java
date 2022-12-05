package io.sudhakar.student.service;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;

import java.util.List;

public interface GenericService {

    ServiceResponse<List<Student>> getAllStudents();

    ServiceResponse<List<Student>> getStudent(int id);

    ServiceResponse<Void> addStudent(Student student);

    ServiceResponse<Void> updateStudent(Student student, int id);

    ServiceResponse<Void> deleteStudent(int id);

    ServiceResponse<Void> addMany(List<Student> students);

    ServiceResponse<Void> deleteMany(List<Integer> ids);

    ServiceResponse<Void> updateMany(List<Integer> ids, Student student);

}