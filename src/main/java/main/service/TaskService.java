package main.service;

import main.model.Task;
import main.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public ArrayList<Task> findAllTasks(){
        Iterable<Task> taskIterable = taskRepository.findAll();

        ArrayList<Task> tasks = new ArrayList<>();
        taskIterable.forEach(tasks::add);
        return tasks;
    }

    @Transactional
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    @Transactional
    public int addTask(Task task){
        return taskRepository.save(task).getId();
    }

    @Transactional(readOnly = true)
    public Task getTask(int id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(!optionalTask.isPresent()){
            throw new RuntimeException("The task by id was not found!");
        }
        return optionalTask.get();
    }

    @Transactional
    public void updateTask(Task task) {
        Optional<Task> optionalTask = taskRepository.findById(task.getId());
        if(!optionalTask.isPresent()){
            throw new RuntimeException("The task by id for update was not found!");
        }
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(!optionalTask.isPresent()){
            throw new RuntimeException("The task by id for delete was not found!");
        }
        taskRepository.deleteById(id);
    }
}
