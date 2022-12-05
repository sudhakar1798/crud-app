package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.Address;
import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Student;
import io.sudhakar.student.entity.AddressEntity;
import io.sudhakar.student.entity.StudentEntity;
import io.sudhakar.student.repository.StudentRepository;
import io.sudhakar.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ServiceResponse<List<Student>> getAllStudent() {
        log.info("operation = getAllStudent, result = IN_PROGRESS");

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();

        try {
            List<StudentEntity> studentEntities = (List<StudentEntity>) studentRepository.findAll();
            List<Student> students = new ArrayList<>();

            for (StudentEntity studentEntity : studentEntities) {
                Student student = new Student();
                BeanUtils.copyProperties(studentEntity, student);

                Set<Address> addresses = Optional.ofNullable(studentEntity.getAddressEntities())
                        .orElse(Collections.emptySet())
                        .stream().map(addressEntity -> {
                            Address address = new Address();
                            BeanUtils.copyProperties(addressEntity, address);
                            return address;
                        })
                        .collect(Collectors.toSet());
                student.setAddresses(addresses);
                students.add(student);
            }

            log.info("operation = getAllStudent, result = SUCCESS");

            serviceResponse.setData(students);
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getAllStudent, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Student> getStudent(int id) {
        log.info("operation = getStudent, result = IN_PROGRESS");

        ServiceResponse<Student> serviceResponse = new ServiceResponse<>();

        try {
            Optional<StudentEntity> studentEntity = studentRepository.findById(id);

            Student student = new Student();
            BeanUtils.copyProperties(studentEntity.get(), student);

            Set<Address> addresses = Optional.ofNullable(studentEntity.get().getAddressEntities())
                    .orElse(Collections.emptySet())
                    .stream().map(addressEntity -> {
                        Address address = new Address();
                        BeanUtils.copyProperties(addressEntity, address);
                        return address;
                    })
                    .collect(Collectors.toSet());
            student.setAddresses(addresses);

            log.info("operation = getStudent, result = SUCCESS");

            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(student);

        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getStudent, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> addStudent(Student student) {
        log.info("operation = addStudent, result = IN_PROGRESS");

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        try {
            StudentEntity studentEntity = new StudentEntity();
            BeanUtils.copyProperties(student, studentEntity);

            Set<AddressEntity> addressEntitySet = new HashSet<>();

            for (Address address : student.getAddresses()) {
                AddressEntity addressEntity = new AddressEntity();
                BeanUtils.copyProperties(address, addressEntity);
                addressEntitySet.add(addressEntity);
            }

            studentEntity.setAddressEntitiesList(addressEntitySet);

            studentRepository.save(studentEntity);

            log.info("operation = addStudent, result = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = addStudent, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> updateStudent(int id, Student student) {


        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = updateStudent, result = IN_PROGRESS");

            Optional<StudentEntity> studentEntity = studentRepository.findById(id);

            StudentEntity students = studentEntity.get();

            students.setAge(student.getAge());
            students.setCourse(student.getCourse());
            students.setPlace(student.getPlace());
            students.setName(student.getName());

            Set<AddressEntity> addressEntitySet = new HashSet<>();

            for (Address address : student.getAddresses()) {
                AddressEntity addressEntity = new AddressEntity();

                BeanUtils.copyProperties(address, addressEntity);
                addressEntitySet.add(addressEntity);
            }

            students.setAddressEntitiesList(addressEntitySet);
            studentRepository.save(students);

            log.info("operation = updateStudent, result = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = updateStudent, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> deleteStudent(int id) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = deleteStudent, result = IN_PROGRESS");
            studentRepository.deleteById(id);

            log.info("operation = deleteStudent, result = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            return serviceResponse;
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = deleteStudent, result = ERROR", e);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse<Page<Student>> getWithPaginationAndSorting(Pageable pageable) {
        log.info("operation = getAllStudentWithPaginationAndSorting, result = IN_PROGRESS");

        ServiceResponse<Page<Student>> serviceResponse = new ServiceResponse<>();

        try {
            Page<StudentEntity> studentEntities = studentRepository.findAll(pageable);

            List<Student> students = new ArrayList<>();

            for (StudentEntity studentEntity : studentEntities) {
                Student student = new Student();

                BeanUtils.copyProperties(studentEntity, student);
                Set<Address> addresses = Optional.ofNullable(studentEntity.getAddressEntities())
                        .orElse(Collections.emptySet())
                        .stream().map(addressEntity -> {
                            Address address = new Address();
                            BeanUtils.copyProperties(addressEntity, address);
                            return address;
                        })
                        .collect(Collectors.toSet());
                student.setAddresses(addresses);
                students.add(student);
            }

            Page<Student> page = new PageImpl<>(students, pageable, students.size());

            serviceResponse.setData(page);
            serviceResponse.setHttpStatus(HttpStatus.OK);

            log.info("operation = getAllStudentWithPaginationAndSorting, result = SUCCESS");

        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getAllStudentWithPaginationAndSorting, result = ERROR", e);
        }
        return serviceResponse;
    }

}