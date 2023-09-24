package com.github.moinmarcell.backend.todo;

import com.github.moinmarcell.backend.util.IdService;
import com.github.moinmarcell.backend.util.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;
    private final IdService idService;
    private final TimeService timeService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addNewTodo(@RequestBody TodoRequest todoRequest) {
        return todoRepository.save(
                new Todo(
                        idService.generateId(),
                        todoRequest.title(),
                        todoRequest.description(),
                        todoRequest.author(),
                        timeService.getLocalDateTimeNow()
                )
        );
    }

    @PutMapping("/{id}")
    public Todo updateTodo(
            @PathVariable String id,
            @RequestBody TodoRequest todoRequest
    ) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("Todo with given id does not exist!");
        }
        return todoRepository.save(
                new Todo(
                        id,
                        todoRequest.title(),
                        todoRequest.description(),
                        todoRequest.author(),
                        timeService.getLocalDateTimeNow()
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable String id) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("Todo with given id does not exist!");
        }
        todoRepository.deleteById(id);
    }
}
