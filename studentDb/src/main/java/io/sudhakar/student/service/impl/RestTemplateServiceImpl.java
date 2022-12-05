package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.Employee;
import io.sudhakar.student.dto.EmployeeResponseGetAll;
import io.sudhakar.student.dto.EmployeeResponse;
import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.urldetails.RestTemplateUtil;
import io.sudhakar.student.service.RestTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    private final RestTemplateUtil restTemplateUtil;

    public RestTemplateServiceImpl(RestTemplateUtil restTemplateUtil) {
        this.restTemplateUtil = restTemplateUtil;
    }

    public ServiceResponse<EmployeeResponseGetAll> getAllEmployee() {
        ServiceResponse<EmployeeResponseGetAll> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = getAllEmployee, status = IN_PROCESS");
            String urlName = "findAll";
            EmployeeResponseGetAll employeeResponse = restTemplateUtil.get(urlName, EmployeeResponseGetAll.class);

            log.info("operation = getAllEmployee, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(employeeResponse);
            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = getAllEmployee, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

    public ServiceResponse<EmployeeResponse> getEmployeeById(int id) {
        ServiceResponse<EmployeeResponse> serviceResponse = new ServiceResponse<>();
        try {
            log.info("operation = getEmployeeById, status = IN_PROCESS");
            String urlName = "findById";
            EmployeeResponse employeeResponse = restTemplateUtil.get(urlName, EmployeeResponse.class, id);

            log.info("operation = getEmployeeById, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(employeeResponse);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = getEmployeeById, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

    public ServiceResponse<EmployeeResponse> addEmployee(Employee employee) {
        ServiceResponse<EmployeeResponse> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = addEmployee, status = IN_PROCESS");
            String urlName = "add";
            EmployeeResponse employeeResponse = restTemplateUtil.post(urlName, employee, EmployeeResponse.class);

            log.info("operation = addEmployee, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(employeeResponse);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = addEmployee, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> updateEmployee(Employee employee, int id) {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = updateEmployee, status = IN_PROCESS");
            String urlName = "update";
            restTemplateUtil.put(urlName, employee, id);

            log.info("operation = updateEmployee, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = updateEmployee, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> deleteEmployee(int id) {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        try {
            log.info("operation = deleteEmployee, status = IN_PROCESS");
            String urlName = "delete";
            restTemplateUtil.delete(urlName, id);

            log.info("operation = deleteEmployee, status = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = deleteEmployee, status = ERROR", e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }
}
