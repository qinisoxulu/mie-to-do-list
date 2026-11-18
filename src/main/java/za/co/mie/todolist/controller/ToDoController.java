package za.co.mie.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.mie.todolist.dto.ToDoRequestDTO;
import za.co.mie.todolist.model.ToDoModel;
import za.co.mie.todolist.model.UserModel;
import za.co.mie.todolist.service.ToDoService;
import za.co.mie.todolist.service.UserService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;
    private final UserService userService;

    public ToDoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ToDoModel> createTodo(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody ToDoRequestDTO dto) {
        UserModel user = userService.getUserByToken(authHeader);
        ToDoModel created = toDoService.createTodo(user, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ToDoModel>> getMyTodos(@RequestHeader("Authorization") String authHeader) {
        UserModel user = userService.getUserByToken(authHeader);
        List<ToDoModel> todos = toDoService.getTodosForUser(user);
        return ResponseEntity.ok(todos);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ToDoModel> markComplete(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UserModel user = userService.getUserByToken(authHeader);
        ToDoModel updated = toDoService.markAsCompleted(id, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id) {
        UserModel user = userService.getUserByToken(authHeader);
        toDoService.deleteTodo(id, user);
        return ResponseEntity.noContent().build();
    }
}