package za.co.mie.todolist.service;

import org.springframework.stereotype.Service;
import za.co.mie.todolist.dto.ToDoRequestDTO;
import za.co.mie.todolist.model.ToDoModel;
import za.co.mie.todolist.model.UserModel;
import za.co.mie.todolist.repository.ToDoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDoModel createTodo(UserModel user, ToDoRequestDTO dto) {
        ToDoModel todo = new ToDoModel();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setDueDate(LocalDateTime.parse(dto.getDueDate()));
        todo.setCompleted(false);
        todo.setUser(user);
        return toDoRepository.save(todo);
    }

    public List<ToDoModel> getTodosForUser(UserModel user) {
        return toDoRepository.findAll().stream().filter(todo -> todo.getUser().getId().equals(user.getId())).toList();
    }

    public ToDoModel markAsCompleted(UUID todoId, UserModel user) {
        ToDoModel todo = toDoRepository.findById(todoId).orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to modify this todo");
        }

        todo.setCompleted(true);
        return toDoRepository.save(todo);
    }

    public void deleteTodo(UUID todoId, UserModel user) {
        ToDoModel todo = toDoRepository.findById(todoId).orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this todo");
        }

        toDoRepository.delete(todo);
    }
}