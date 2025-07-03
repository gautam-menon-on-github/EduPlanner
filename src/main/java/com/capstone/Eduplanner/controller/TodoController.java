package com.capstone.Eduplanner.controller;

import com.capstone.Eduplanner.model.TodoItem;
import com.capstone.Eduplanner.model.User;
import com.capstone.Eduplanner.service.TodoService;
import com.capstone.Eduplanner.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showTodoList(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).get();
        model.addAttribute("todos", todoService.getTodosByUser(user));
        return "todo-list";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String description, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).get();

        TodoItem item = new TodoItem();
        item.setDescription(description);
        item.setCompleted(false);
        item.setUser(user);

        todoService.addTodo(item);
        return "redirect:/todos";
    }

    @PostMapping("/complete/{id}")
    public String markComplete(@PathVariable int id, HttpSession session) {
        TodoItem item = todoService.getTodoById(id);
        Integer userId = (Integer) session.getAttribute("userId");

        if (item != null && item.getUser().getId() == userId) {
            item.setCompleted(true);
            todoService.addTodo(item); // Re-save
        }
        return "redirect:/todos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        TodoItem item = todoService.getTodoById(id);
        Integer userId = (Integer) session.getAttribute("userId");

        if (item != null && item.getUser().getId() == userId) {
            todoService.deleteTodo(id);
        }
        return "redirect:/todos";
    }
}
