package io.sudhakar.student.service;

import io.sudhakar.student.dto.Employee;
import io.sudhakar.student.dto.EmployeeResponseGetAll;
import io.sudhakar.student.dto.EmployeeResponse;
import io.sudhakar.student.dto.ServiceResponse;

public interface RestTemplateService {

    ServiceResponse<EmployeeResponseGetAll> getAllEmployee();

    ServiceResponse<EmployeeResponse> getEmployeeById(int id);

    ServiceResponse<EmployeeResponse> addEmployee(Employee employee);

    ServiceResponse<Void> updateEmployee(Employee employee, int id);

    ServiceResponse<Void> deleteEmployee(int id);

}
