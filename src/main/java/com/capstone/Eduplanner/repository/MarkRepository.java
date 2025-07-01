package com.capstone.Eduplanner.repository;

import com.capstone.Eduplanner.model.Mark;
import com.capstone.Eduplanner.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {
    List<Mark> findBySubject(Subject subject);
}
