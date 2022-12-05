package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.*;
import io.sudhakar.student.entity.TaskEntity;
import io.sudhakar.student.repository.TaskRepository;
import io.sudhakar.student.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    TaskServiceImpl taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserUtil userUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository, userUtil);
    }

    /*
     Given findByUserId(),getAllTask()
     Then  ServiceResponse,tasks
     Scenario SUCCESS
     */
    @Test
    void getAllTaskOk() {

        TaskEntity task = new TaskEntity();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.doReturn(tasks)
                .when(taskRepository).findByUserId(user.getUserId());

        ServiceResponse<List<Task>> serviceResponse = taskService.getAllTask();

        serviceResponse.setHttpStatus(HttpStatus.OK);
        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());


        assertEquals(1, serviceResponse.getData().get(0).getId());
        assertEquals("description", serviceResponse.getData().get(0).getDescription());
    }

    /*
    Given findByUserId,getAllTask()
    Then  ServiceResponse,tasks
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getAllTaskInternalServerError() {

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.when(taskRepository.findByUserId(user.getUserId()))
                .thenThrow(new NullPointerException(""));
        ServiceResponse<List<Task>> serviceResponse = taskService.getAllTask();

        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given findById(),getTask()
    Then  ServiceResponse,task
    Scenario SUCCESS
     */
    @Test
    void getTaskOk() {
        TaskEntity task = new TaskEntity();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.doReturn(tasks)
                .when(taskRepository).findByUserIdAndId(user.getUserId(), 1);

        ServiceResponse<List<Task>> serviceResponse = taskService.getTask(1);

        serviceResponse.setHttpStatus(HttpStatus.OK);
        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

        assertEquals(1, serviceResponse.getData().get(0).getId());
        assertEquals("description", serviceResponse.getData().get(0).getDescription());
    }

    /*
    Given findById(),getTask()
    Then  ServiceResponse,task
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getTaskInternalServerError() {

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.when(taskRepository.findByUserIdAndId(user.getUserId(), 1))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<List<Task>> serviceResponse = taskService.getTask(1);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given addTask()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void addTask() {
        User user = new User("user", "pass", 1, new ArrayList<>());
        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(user.getUserId());

        Mockito.when(taskRepository.save(Mockito.any(TaskEntity.class)))
                .thenReturn(null);

        ServiceResponse<Void> serviceResponse = taskService.addTask(task);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());

    }

    /*
    Given addTask()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void addTaskInternalServerError() {
        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(user.getUserId());

        Mockito.doThrow(new NullPointerException(""))
                .when(taskRepository).save(Mockito.any(TaskEntity.class));

        ServiceResponse<Void> serviceResponse = taskService.addTask(task);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given updateTask()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void updateTaskOk() {
        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(user.getUserId());

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1);
        taskEntity.setDescription("description");
        taskEntity.setStatus("completed");
        taskEntity.setName("name");
        taskEntity.setUserId(user.getUserId());

        Mockito.when(taskRepository.save(taskEntity))
                .thenReturn(null);

        ServiceResponse<Void> serviceResponse = taskService.updateTask(1, task);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given updateTask()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void updateTaskInternalServerError() {
        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(user.getUserId());

        Mockito.when(taskRepository.save(Mockito.any(TaskEntity.class)))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Void> serviceResponse = taskService.updateTask(1, task);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given deleteByUserIdAndId(),deleteTask()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void deleteTaskOk() {
        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        TaskEntity task = new TaskEntity();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        doNothing().when(taskRepository).deleteByUserIdAndId(user.getUserId(), 1);

        ServiceResponse<Void> serviceResponse = taskService.deleteTask(1);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given deleteByUserIdAndId(),deleteTask()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void deleteTaskInternalServerError() {

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        doThrow(new NullPointerException(""))
                .when(taskRepository).deleteByUserIdAndId(user.getUserId(), 1);

        ServiceResponse<Void> serviceResponse = taskService.deleteTask(1);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given saveAll(),addManyTask()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void addManyTaskOk() {

        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.when(taskRepository.saveAll(Mockito.anyList()))
                .thenReturn(null);

        ServiceResponse<Void> serviceResponse = taskService.addManyTask(taskList);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given saveAll(),addManyTask()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void addManyTaskInternalServerError() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.when(taskRepository.saveAll(Mockito.anyList()))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Void> serviceResponse = taskService.addManyTask(taskList);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given saveAll(),updateManyTask()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void updateManyTaskOk() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.when(taskRepository.saveAll(Mockito.anyList()))
                .thenReturn(null);

        ServiceResponse<Void> serviceResponse = taskService.updateManyTask(taskList);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given saveAll(),updateManyTask()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void updateManyTaskInternalServerError() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Mockito.when(taskRepository.saveAll(Mockito.anyList()))
                .thenThrow(new NullPointerException(""));

        ServiceResponse<Void> serviceResponse = taskService.updateManyTask(taskList);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }

    /*
    Given deleteByUserIdAndIdIn(),deleteManyTask()
    Then  ServiceResponse
    Scenario SUCCESS
    */
    @Test
    void deleteManyTaskOk() {

        List<Integer> ids = new ArrayList<>();
        ids.add(1);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        doNothing().when(taskRepository).deleteByUserIdAndIdIn(user.getUserId(), ids);

        ServiceResponse<Void> serviceResponse = taskService.deleteManyTask(ids);
        serviceResponse.setHttpStatus(HttpStatus.OK);

        assertEquals(HttpStatus.OK, serviceResponse.getHttpStatus());
    }

    /*
    Given deleteByUserIdAndIdIn(),deleteManyTask()
    Then  ServiceResponse
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void deleteManyTaskInternalServerError() {

        List<Integer> ids = new ArrayList<>();
        ids.add(1);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        doThrow(new NullPointerException("")).when(taskRepository).deleteByUserIdAndIdIn(user.getUserId(), ids);

        ServiceResponse<Void> serviceResponse = taskService.deleteManyTask(ids);
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getHttpStatus());
    }
}