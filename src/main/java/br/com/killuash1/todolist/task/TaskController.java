package br.com.killuash1.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/tasks")
public class TaskController {
  
  @Autowired
  private TaskService taskService;
  @Autowired
  private  ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    System.out.println("Chegou no controller");
    return this.taskService.create(taskModel, request);
  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request){

      return this.taskService.list(request);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody @Valid TaskModel taskModel, HttpServletRequest request, @PathVariable  UUID id){
    return this.taskService.update(taskModel, request, id);
  }
}
