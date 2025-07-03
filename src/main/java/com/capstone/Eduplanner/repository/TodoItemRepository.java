package com.capstone.Eduplanner.repository;

import com.capstone.Eduplanner.model.TodoItem;
import com.capstone.Eduplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {
    List<TodoItem> findByUser(User user);
}
