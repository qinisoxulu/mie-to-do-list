package za.co.mie.todolist.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.mie.todolist.repository.ToDoRepository;
import java.time.LocalDateTime;

@Service
public class ToDoSchedulerService {

    private final ToDoRepository toDoRepository;

    public ToDoSchedulerService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void autoCompleteOverdueTodos() {
        LocalDateTime now = LocalDateTime.now();
        int count = toDoRepository.markOverdueToDosAsCompleted(now);
        if (count > 0) {
            System.out.println("Scheduler: Automatically completed " + count + " overdue todos.");
        }
    }
}