package com.capstone.Eduplanner.service;

import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.model.User;
import com.capstone.Eduplanner.repository.SubjectRepository;
import com.capstone.Eduplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    // find subjects by user id.
    public List<Subject> getSubjectsByUserId(int id) {
        User user = userRepository.findById(id).orElse(null);
        return subjectRepository.findByUser(user);
    }

    // add individual subjects.
    public void addSubject(int userId, Subject subject) {
        User user = userRepository.findById(userId).orElse(null);
        subject.setUser(user);
        subjectRepository.save(subject);
    }

    // get individual subject by its id.
    public Subject getSubjectById(int id) {
        return subjectRepository.findById(id).orElse(null);
    }

    // update a subject
    public void updateSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    // delete a subject by its id.
    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }
}
