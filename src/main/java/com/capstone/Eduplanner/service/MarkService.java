package com.capstone.Eduplanner.service;

import com.capstone.Eduplanner.model.Mark;
import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    public List<Mark> getMarksBySubject(Subject subject) {
        return markRepository.findBySubject(subject);
    }

    public void addMark(Mark mark) {
        markRepository.save(mark);
    }

    public Mark getMarkById(int id) {
        return markRepository.findById(id).orElse(null);
    }

    public void saveMark(Mark mark) {
        markRepository.save(mark);
    }

    public void updateMark(Mark mark) {
        markRepository.save(mark);
    }

    public void deleteMark(int id) {
        markRepository.deleteById(id);
    }
}
