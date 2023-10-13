package br.com.killuash1.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
   * ID
   * Usuario
   * Descricao
   * Titulo
   * Data de inicio
   * Data de termino
   * Prioridade
   * 
   */

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
  
  
  @Id
  @GeneratedValue(generator = "UUID")
   private UUID id;
   private String description;

   @Column(length = 50)
   private String title;
   private LocalDateTime startAt;
   private LocalDateTime endAt;
   private String priority;
  
   private LocalDateTime createdAt;

   private UUID userId;

   public void setTitle(String title) throws Exception {
     if (title.length() > 50) {
       throw new Exception("O campo deve ter no maximo 50 caracteres");
     }
    this.title = title;
   }
}
