package za.co.mie.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.mie.todolist.model.ToDoModel;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoModel, UUID> {

    @Modifying
    @Query("UPDATE ToDoModel todo SET todo.completed = true WHERE todo.dueDate <= :now AND todo.completed = false")
    int markOverdueToDosAsCompleted(@Param("now") LocalDateTime now);
}