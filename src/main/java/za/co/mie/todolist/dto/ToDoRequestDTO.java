package za.co.mie.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDoRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private String dueDate;
}