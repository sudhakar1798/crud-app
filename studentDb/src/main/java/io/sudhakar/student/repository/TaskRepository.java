package io.sudhakar.student.repository;

import io.sudhakar.student.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Integer> {

    List<TaskEntity> findByUserId(long userId);

    List<TaskEntity> findByUserIdAndId(long userId, int id);

    void deleteByUserIdAndId(long userId, int id);

    void deleteByUserIdAndIdIn(long userId, List<Integer> id);

}



