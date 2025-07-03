package com.capstone.Eduplanner.service;

import com.capstone.Eduplanner.model.TodoItem;
import com.capstone.Eduplanner.model.User;
import com.capstone.Eduplanner.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoItemRepository todoRepo;

    public List<TodoItem> getTodosByUser(User user) {
        return todoRepo.findByUser(user);
    }

    public void addTodo(TodoItem item) {
        todoRepo.save(item);
    }

    public void deleteTodo(int id) {
        todoRepo.deleteById(id);
    }

    public TodoItem getTodoById(int id) {
        return todoRepo.findById(id).orElse(null);
    }
}
