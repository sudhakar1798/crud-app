package io.sudhakar.student.service;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Task;

import java.util.List;

public interface TaskService {

    ServiceResponse<List<Task>> getAllTask();

    ServiceResponse<List<Task>> getTask(int id);

    ServiceResponse<Void> addTask(Task task);

    ServiceResponse<Void> updateTask(int id, Task task);

    ServiceResponse<Void> deleteTask(int id);

    ServiceResponse<Void> addManyTask(List<Task> tasks);

    ServiceResponse<Void> updateManyTask(List<Task> task);

    ServiceResponse<Void> deleteManyTask(List<Integer> ids);

}

