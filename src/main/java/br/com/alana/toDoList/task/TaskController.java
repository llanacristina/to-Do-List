package br.com.alana.toDoList.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepositoy taskRepositoy;

    @PostMapping("/")
    public TaskModel create (@RequestBody TaskModel taskModel){
        var task = this.taskRepositoy.save(taskModel);
        return task;

    }
    
}
