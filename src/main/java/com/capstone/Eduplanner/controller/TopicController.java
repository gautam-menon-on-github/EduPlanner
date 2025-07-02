package com.capstone.Eduplanner.controller;

import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.model.Topic;
import com.capstone.Eduplanner.service.SubjectService;
import com.capstone.Eduplanner.service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/add/{subjectId}")
    public String showAddForm(@PathVariable int subjectId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Subject subject = subjectService.getSubjectById(subjectId);

        if (userId == null || subject == null || subject.getUser().getId() != userId) {
            return "error-403";
        }

        Topic topic = new Topic();
        topic.setSubject(subject);

        model.addAttribute("topic", topic);
        model.addAttribute("subjectId", subjectId); // Needed for cancel/back link
        return "topic-form";
    }


    @PostMapping("/add/{subjectId}")
    public String addTopic(@PathVariable int subjectId,
                           @ModelAttribute Topic topic,
                           HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Subject subject = subjectService.getSubjectById(subjectId);

        if (userId == null || subject == null || subject.getUser().getId() != userId) {
            return "error-403";
        }

        topic.setSubject(subject);
        topicService.addTopic(topic);

        return "redirect:/subjects/" + subjectId + "/details";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Topic topic = topicService.getTopicById(id);

        if (topic == null || topic.getSubject() == null || topic.getSubject().getUser().getId() != userId) {
            return "error-403";
        }

        model.addAttribute("topic", topic);
        model.addAttribute("subjectId", topic.getSubject().getId()); // needed for cancel/back
        return "topic-form";
    }


    @PostMapping("/edit/{id}")
    public String editTopic(@PathVariable int id,
                            @ModelAttribute Topic updatedTopic,
                            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Topic existingTopic = topicService.getTopicById(id);

        if (userId == null || existingTopic == null ||
                existingTopic.getSubject() == null ||
                existingTopic.getSubject().getUser().getId() != userId) {
            return "error-403";
        }

        existingTopic.setTopicName(updatedTopic.getTopicName());
        existingTopic.setCompleted(updatedTopic.isCompleted());

        topicService.updateTopic(existingTopic);

        return "redirect:/subjects/" + existingTopic.getSubject().getId() + "/details";
    }


    @GetMapping("/delete/{id}")
    public String deleteTopic(@PathVariable int id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Topic topic = topicService.getTopicById(id);

        if (topic == null || topic.getSubject() == null || topic.getSubject().getUser().getId() != userId) {
            return "error-403";
        }

        int subjectId = topic.getSubject().getId();
        topicService.deleteTopic(id);
        return "redirect:/subjects/" + subjectId + "/details";
    }
}
