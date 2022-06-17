package main.controller;

import main.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;
    private static Logger rootLogger;

    @Autowired
    public TaskController(TaskService taskService) {
        rootLogger = LogManager.getRootLogger();
        this.taskService = taskService;
    }

    @GetMapping("/tasks/")
    public List<Task> list(){
        rootLogger.info("findAllTasks");
        return taskService.findAllTasks();
    }

    @DeleteMapping("/tasks")
    public ResponseEntity<?> remove(){
        rootLogger.info("deleteAllTasks");
        taskService.deleteAllTasks();
        return new ResponseEntity<>(taskService, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public int add(@NotNull Task task){
        rootLogger.info("addTask " + task);
        return taskService.addTask(task);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<?> removeTask(@PathVariable int id){
        try {
            taskService.deleteTask(id);
        }catch (RuntimeException ex){
            rootLogger.warn(ex.getMessage() + " - id = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        rootLogger.info("deleteTask " + id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Task task;
        try {
            task = taskService.getTask(id);
        }catch (RuntimeException ex){
            rootLogger.warn(ex.getMessage() + " - id = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        rootLogger.info("getTask " + id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<?> update(@PathVariable int id, Task task){
        try {
            task.setId(id);
            taskService.updateTask(task);
        }catch (RuntimeException ex){
            rootLogger.warn(ex.getMessage() + " - id = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        rootLogger.info("updateTask " + id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
