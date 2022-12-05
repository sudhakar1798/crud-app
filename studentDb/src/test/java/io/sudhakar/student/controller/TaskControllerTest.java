package io.sudhakar.student.controller;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.StudentResponse;
import io.sudhakar.student.dto.Task;
import io.sudhakar.student.dto.User;
import io.sudhakar.student.service.TaskService;
import io.sudhakar.student.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    TaskController taskController;

    @Mock
    TaskService taskService;

    @Mock
    UserUtil userUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskController = new TaskController(taskService, userUtil);
    }

    /*
    Given getAllTask()
    Then  ResponseEntity<StudentResponse<List<Task>>>
    Scenario OK
    */
    @Test
    void getAllTaskOk() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<List<Task>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(tasks);

        Mockito.when(taskService.getAllTask())
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Task>>> responseEntity = taskController.getAllTask();
        assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals("name", (responseEntity.getBody()).getData().get(0).getName());
        assertEquals("description", (responseEntity.getBody()).getData().get(0).getDescription());
    }

    /*
    Given getAllTask()
    Then  ResponseEntity<StudentResponse<List<Task>>>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getAllTaskInternalServerError() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<List<Task>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setData(tasks);

        Mockito.when(taskService.getAllTask())
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Task>>> responseEntity = taskController.getAllTask();
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given getTask()
    Then  ResponseEntity<StudentResponse<List<Task>>>
    Scenario SUCCESS
    */
    @Test
    void getTaskOk() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<List<Task>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setData(tasks);

        when(taskService.getTask(1))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Task>>> responseEntity = taskController.getTask(1);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given getTask()
    Then  ResponseEntity<StudentResponse<List<Task>>>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void getTaskInternalServerError() {
        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(1);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<List<Task>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setData(tasks);

        when(taskService.getTask(1))
                .thenReturn(serviceResponse);

        ResponseEntity<StudentResponse<List<Task>>> responseEntity = taskController.getTask(1);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given addTask()
    Then  ServiceResponse<Void>
    Scenario SUCCESS
    */
    @Test
    void addTaskOk() {

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        Task task = new Task();
        task.setId(1);
        task.setDescription("description");
        task.setStatus("completed");
        task.setName("name");
        task.setUserId(user.getUserId());

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(taskService.addTask(task))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.addTask(task);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given addTask()
    Then  ServiceResponse<Void>
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(taskService.addTask(Mockito.any(Task.class)))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.addTask(task);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given updateTask()
    Then  ServiceResponse<Void>
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(taskService.updateTask(1, task))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.updateTask(1, task);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given updateTask()
    Then  ServiceResponse<Void>
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(taskService.updateTask(1, task))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.updateTask(1, task);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteTask()
    Then  ServiceResponse<Void>
    Scenario SUCCESS
    */
    @Test
    void deleteTaskOk() {

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(taskService.deleteTask(1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.deleteTask(1);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteTask()
    Then  ServiceResponse<Void>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void deleteTaskInternalServerError() {

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(taskService.deleteTask(1))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.deleteTask(1);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given addManyTask()
    Then  ServiceResponse<Void>
    Scenario SUCCESS
    */
    @Test
    void addMAnyTaskOk() {
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(taskService.addManyTask(taskList))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.addManyTask(taskList);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given addManyTask()
    Then  ServiceResponse<Void>
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(taskService.addManyTask(Mockito.anyList()))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.addManyTask(taskList);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given updateManyTask()
    Then  ServiceResponse<Void>
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(taskService.updateManyTask(taskList))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.updateManyTask(taskList);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given updateManyTask()
    Then  ServiceResponse<Void>
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

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(taskService.updateManyTask(Mockito.anyList()))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.updateManyTask(taskList);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteManyTask()
    Then  ServiceResponse<Void>
    Scenario SUCCESS
    */
    @Test
    void deleteManyOk() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.OK);

        when(taskService.deleteManyTask(ids))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.deleteManyTask(ids);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /*
    Given deleteManyTask()
    Then  ServiceResponse<Void>
    Scenario ERROR (INTERNAL_SERVER_ERROR)
    */
    @Test
    void deleteManyInternalServerError() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);

        User user = new User("user", "pass", 1, new ArrayList<>());

        Mockito.when(userUtil.getLoginUser())
                .thenReturn(user);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        when(taskService.deleteManyTask(Mockito.anyList()))
                .thenReturn(serviceResponse);

        ResponseEntity<Void> responseEntity = taskController.deleteManyTask(ids);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }
}