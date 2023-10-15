package br.com.alana.toDoList.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepositoy extends JpaRepository<TaskModel,UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
    
}
