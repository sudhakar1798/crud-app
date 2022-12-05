package io.sudhakar.student.repository.queryDetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:queries/queries.properties")
@Getter
public class Queries {

    private final String findAll;
    private final String findById;
    private final String add;
    private final String update;
    private final String delete;
    private final String insertMany;
    private final String deleteMany;
    private final String updateMany;
    private final String addManyTask;


    public Queries(@Value("${FIND_ALL_STUDENT}") String findAll,
                   @Value("${INSERT_MANY}") String insertMany,
                   @Value("${UPDATE}") String update,
                   @Value("${FIND_BY_ID}") String findById,
                   @Value("${INSERT}") String add,
                   @Value("${DELETE}") String delete,
                   @Value("${UPDATE_MANY}") String updateMany,
                   @Value("${DELETE_MANY}") String deleteMany,
                   @Value("$INSERT_MANY_TASK") String addManyTask) {

        this.findAll = findAll;
        this.findById = findById;
        this.add = add;
        this.update = update;
        this.delete = delete;
        this.insertMany = insertMany;
        this.deleteMany = deleteMany;
        this.updateMany = updateMany;
        this.addManyTask = addManyTask;
    }

}

