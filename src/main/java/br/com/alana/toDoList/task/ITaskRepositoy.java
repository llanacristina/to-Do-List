package br.com.alana.toDoList.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepositoy extends JpaRepository<TaskModel,UUID>{
    
}
