package com.capstone.Eduplanner.service;

import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.model.Topic;
import com.capstone.Eduplanner.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getTopicsBySubject(Subject subject) {
        return topicRepository.findBySubject(subject);
    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic getTopicById(int id) {
        return topicRepository.findById(id).orElse(null);
    }

    public Topic updateTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public void deleteTopic(int id) {
        topicRepository.deleteById(id);
    }

}
