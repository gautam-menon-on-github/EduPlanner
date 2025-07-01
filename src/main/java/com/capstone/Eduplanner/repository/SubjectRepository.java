package com.capstone.Eduplanner.repository;

import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findByUser(User user);
}
