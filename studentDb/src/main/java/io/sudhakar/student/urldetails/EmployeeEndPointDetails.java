package io.sudhakar.student.urldetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmployeeEndPointDetails {
    private final String findAll;
    private final String findById;
    private final String add;
    private final String update;
    private final String delete;

    public EmployeeEndPointDetails(@Value("${GET_ALL}") String findAll,
                                   @Value("${GET_BY_ID}") String findById,
                                   @Value("${ADD}") String add,
                                   @Value("${UPDATE}") String update,
                                   @Value("${DELETE}") String delete) {

        this.findAll = findAll;
        this.findById = findById;
        this.add = add;
        this.update = update;
        this.delete = delete;
    }

}

