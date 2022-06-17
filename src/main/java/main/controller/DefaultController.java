package main.controller;

import java.util.ArrayList;
import main.model.Task;
import main.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    private final TaskService taskService;

    @Autowired
    public DefaultController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        ArrayList<Task> tasks = taskService.findAllTasks();

        model.addAttribute("tasks", tasks);

        return "index";
    }
}