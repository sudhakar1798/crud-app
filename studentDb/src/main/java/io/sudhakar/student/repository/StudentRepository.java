package io.sudhakar.student.repository;

import io.sudhakar.student.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<StudentEntity, Integer>, CrudRepository<StudentEntity, Integer> {

}