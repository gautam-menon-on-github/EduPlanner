package com.capstone.Eduplanner.controller;

import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.model.User;
import com.capstone.Eduplanner.service.MarkService;
import com.capstone.Eduplanner.service.SubjectService;
import com.capstone.Eduplanner.service.TopicService;
import com.capstone.Eduplanner.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private MarkService markService;

    @Autowired
    private TopicService topicService;

    @GetMapping
    public String viewSubjects(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        model.addAttribute("subjects", subjectService.getSubjectsByUserId(userId));
        return "subjects";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "add-subject";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute Subject subject, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        subjectService.addSubject(userId, subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "edit-subject";
    }

    @PostMapping("/edit")
    public String editSubject(@ModelAttribute Subject subject, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Subject existing = subjectService.getSubjectById(subject.getId());

        // to ensure that some user cannot access data belonging to another user.
        if (existing == null || existing.getUser() == null || existing.getUser().getId() != userId) {
            return "error-403";
        }

        subject.setUser(existing.getUser());
        subjectService.updateSubject(subject);

        return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable int id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Subject subject = subjectService.getSubjectById(id);

        // same thing done aas editSubject to prevent unauthorized access
        if (subject == null || subject.getUser() == null || subject.getUser().getId() != userId) {
            return "error-403";
        }

        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/details")
    public String showSubjectDetails(@PathVariable int id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Subject subject = subjectService.getSubjectById(id);
        if (subject == null || subject.getUser().getId() != userId) {
            return "error-403";
        }

        model.addAttribute("subject", subject);
        model.addAttribute("marks", markService.getMarksBySubject(subject));
        model.addAttribute("topics", topicService.getTopicsBySubject(subject));

        return "subject-detail";
    }


}
