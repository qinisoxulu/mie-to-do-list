package za.co.mie.todolist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "todo")
@NoArgsConstructor
@Getter
@Setter
public class ToDoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    public UserModel user;

    @Column(nullable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    public LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false)
    public String title;

    private String description;

    @Column(nullable = false)
    public boolean completed = false;

    private LocalDateTime dueDate;
}
