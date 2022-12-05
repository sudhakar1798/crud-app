package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.User;
import io.sudhakar.student.repository.TaskRepository;
import io.sudhakar.student.service.TaskService;
import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.Task;
import io.sudhakar.student.entity.TaskEntity;
import io.sudhakar.student.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserUtil userUtil;

    public TaskServiceImpl(TaskRepository taskRepository, UserUtil userUtil) {
        this.taskRepository = taskRepository;
        this.userUtil = userUtil;

    }

    public ServiceResponse<List<Task>> getAllTask() {

        User user = userUtil.getLoginUser();
        log.info("operation = getAllTasks, result = IN_PROGRESS, userId = {}", user.getUserId());
        ServiceResponse<List<Task>> serviceResponse = new ServiceResponse<>();
        try {
            List<TaskEntity> taskEntities = taskRepository.findByUserId(user.getUserId());
            List<Task> tasks = new ArrayList<>();

            for (TaskEntity taskEntity : taskEntities) {
                Task task = new Task();

                BeanUtils.copyProperties(taskEntity, task);
                tasks.add(task);
            }
            log.info("operation = getAllTasks, result = SUCCESS, userId = {}", user.getUserId());
            serviceResponse.setData(tasks);
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getAllTasks, result = ERROR, userId = {}", user.getUserId(), e);
        }
        return serviceResponse;
    }

    public ServiceResponse<List<Task>> getTask(int id) {

        ServiceResponse<List<Task>> serviceResponse = new ServiceResponse<>();
        User user = userUtil.getLoginUser();
        log.info("operation = getTask, result = IN_PROGRESS, userId = {}", user.getUserId());

        try {
            List<TaskEntity> taskEntities = taskRepository.findByUserIdAndId(user.getUserId(), id);
            List<Task> tasks = new ArrayList<>();

            for (TaskEntity taskEntity : taskEntities) {
                Task task = new Task();

                BeanUtils.copyProperties(taskEntity, task);
                tasks.add(task);
            }
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setData(tasks);
            log.info("operation = getTask, result = SUCCESS, userId = {}", user.getUserId());
        } catch (
                Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getTask, result = ERROR, userId = {}", user.getUserId(), e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> addTask(Task task) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        User user = userUtil.getLoginUser();
        log.info("operation = addTask, result = IN_PROGRESS, userId = {}", user.getUserId());
        try {
            TaskEntity taskEntity = new TaskEntity();
            BeanUtils.copyProperties(task, taskEntity);
            taskEntity.setUserId(user.getUserId());

            taskRepository.save(taskEntity);

            log.info("operation = addTask, result = SUCCESS, userId = {}", user.getUserId());
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = addTask, result = ERROR, userId = {}", user.getUserId(), e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> updateTask(int id, Task task) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        User user = userUtil.getLoginUser();
        log.info("operation = updateTask, result = IN_PROGRESS, userId = {}", user.getUserId());

        try {
            List<TaskEntity> taskEntities = taskRepository.findByUserIdAndId(user.getUserId(), id);
            TaskEntity task1 = new TaskEntity();

            for (TaskEntity taskEntity : taskEntities) {

                taskEntity.setName(task.getName());
                taskEntity.setStatus(task.getStatus());
                taskEntity.setDescription(task.getDescription());
                taskEntity.setUserId(user.getUserId());

                BeanUtils.copyProperties(taskEntity, task1);
            }
            taskRepository.save(task1);

            serviceResponse.setHttpStatus(HttpStatus.OK);
            log.info("operation = updateTask, result = SUCCESS, userId = {}", user.getUserId());

        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = updateTask, result = ERROR, userId = {}", user.getUserId(), e);
        }
        return serviceResponse;
    }

    @Transactional
    public ServiceResponse<Void> deleteTask(int id) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        User user = userUtil.getLoginUser();
        try {
            log.info("operation = deleteTask, result = IN_PROGRESS, userId = {}", user.getUserId());

            taskRepository.deleteByUserIdAndId(user.getUserId(), id);

            log.info("operation = deleteTask, result = SUCCESS, userId = {}", user.getUserId());
            serviceResponse.setHttpStatus(HttpStatus.OK);
            return serviceResponse;
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = deleteTask, result = ERROR, userId = {}", user.getUserId(), e);
        }
        return serviceResponse;
    }


    public ServiceResponse<Void> addManyTask(List<Task> tasks) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        User user = userUtil.getLoginUser();
        try {
            log.info("operation = addMany, status = IN_PROGRESS, userId = {}", user.getUserId());

            List<TaskEntity> taskEntityList = new ArrayList<>();

            for (Task task : tasks) {
                TaskEntity taskEntity = new TaskEntity();
                BeanUtils.copyProperties(task, taskEntity);
                taskEntity.setUserId(user.getUserId());

                taskEntityList.add(taskEntity);
            }
            taskRepository.saveAll(taskEntityList);

            log.info("operation = addMany, result = SUCCESS, userId = {}", user.getUserId());
            serviceResponse.setHttpStatus(HttpStatus.OK);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = addMany, status = ERROR, userId = {}", user.getUserId(), e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> updateManyTask(List<Task> tasks) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        User user = userUtil.getLoginUser();
        try {
            log.info("operation = updateMany, status = IN_PROGRESS, userId = {}", user.getUserId());

            List<Integer> ids = Optional.ofNullable(tasks).orElse(Collections.emptyList())
                    .stream().map(Task::getId)
                    .collect(Collectors.toList());

            List<TaskEntity> taskEntityList = new ArrayList<>();

            List<TaskEntity> taskEntities = (List<TaskEntity>) taskRepository.findAllById(ids);

            Map<Integer, TaskEntity> map = Optional.of(taskEntities).orElse(Collections.emptyList())
                    .stream().collect(Collectors.toMap(TaskEntity::getId, Function.identity()));

            for (Task task : tasks) {
                TaskEntity taskEntity = map.getOrDefault(task.getId(), new TaskEntity());
                BeanUtils.copyProperties(task, taskEntity);
                taskEntity.setUserId(user.getUserId());
                taskEntityList.add(taskEntity);
            }
            taskRepository.saveAll(taskEntityList);
            serviceResponse.setHttpStatus(HttpStatus.OK);

            log.info("operation = updateMany, result = SUCCESS, userId = {}", user.getUserId());
            serviceResponse.setHttpStatus(HttpStatus.OK);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = updateMany, status = ERROR, userId = {}", user.getUserId(), e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

    @Transactional
    public ServiceResponse<Void> deleteManyTask(List<Integer> ids) {

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        User user = userUtil.getLoginUser();
        try {
            log.info("operation = deleteMany, status = IN_PROGRESS, userId = {}", user.getUserId());

            taskRepository.deleteByUserIdAndIdIn(user.getUserId(), ids);

            log.info("operation = deleteMany, result = SUCCESS, userId = {}", user.getUserId());
            serviceResponse.setHttpStatus(HttpStatus.OK);

            return serviceResponse;
        } catch (Exception e) {
            log.error("operation = deleteMany, status = ERROR, userId = {}", user.getUserId(), e);
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serviceResponse;
    }

}