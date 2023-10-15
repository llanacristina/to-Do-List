package br.com.alana.toDoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alana.toDoList.utils.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.var;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepositoy taskRepositoy;

    @PostMapping("/")
    public ResponseEntity<?> create (@RequestBody TaskModel taskModel, HttpServletRequest request){
         /* System.out.println("chegou " + request.getAttribute("idUser")); */

        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID)idUser); 

        // VALIDAÇÃO DE DATA E HORA
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter((taskModel.getEndAt()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início e término deve ser maior do que a data atual");
        }
         if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve ser menor do que a data de término");
        }



        var task = this.taskRepositoy.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task); 

    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepositoy.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TaskModel taskModel, HttpServletRequest request,@PathVariable UUID id){

        var task = this.taskRepositoy.findById(id).orElse(null);

        // VALIDAR SE A TAREFA NAO EXISTE 
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }
        var idUser = request.getAttribute("idUser");

        //SE ELA EXISTIR MAS ID USUARIO FOR OUTRO, NAO PERMITIR ALTERAÇÃO
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para alterar essa tarefa");

        }

        utils.copyNonNullProperties(taskModel, task);

        var taskUpdated = this.taskRepositoy.save(task); 
        return ResponseEntity.ok().body(taskUpdated);

    }
    
}
