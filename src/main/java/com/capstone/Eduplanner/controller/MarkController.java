package com.capstone.Eduplanner.controller;

import com.capstone.Eduplanner.model.Mark;
import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.service.MarkService;
import com.capstone.Eduplanner.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marks")
public class MarkController {

    @Autowired
    private MarkService markService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/add/{subjectId}")
    public String showAddForm(@PathVariable int subjectId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Subject subject = subjectService.getSubjectById(subjectId);

        if (userId == null || subject == null || subject.getUser().getId() != userId) {
            return "error/403";
        }

        Mark mark = new Mark();
        mark.setSubject(subject);
        model.addAttribute("mark", mark);
        return "add_mark";
    }

    @PostMapping("/add")
    public String addMark(@ModelAttribute Mark mark, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Subject subject = subjectService.getSubjectById(mark.getSubject().getId());

        if (userId == null || subject == null || subject.getUser().getId() != userId) {
            return "error/403";
        }

        mark.setSubject(subject); // Ensure subject is correct and attached
        markService.addMark(mark);
        return "redirect:/subjects/" + subject.getId() + "/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Mark mark = markService.getMarkById(id);

        if (mark == null || mark.getSubject() == null || mark.getSubject().getUser().getId() != userId) {
            return "error/403";
        }

        model.addAttribute("mark", mark);
        return "edit_mark";
    }

    @PostMapping("/edit")
    public String editMark(@ModelAttribute Mark mark, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Subject subject = subjectService.getSubjectById(mark.getSubject().getId());

        if (userId == null || subject == null || subject.getUser().getId() != userId) {
            return "error/403";
        }

        mark.setSubject(subject);
        markService.updateMark(mark);
        return "redirect:/subjects/" + subject.getId() + "/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteMark(@PathVariable int id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        Mark mark = markService.getMarkById(id);

        if (mark == null || mark.getSubject() == null || mark.getSubject().getUser().getId() != userId) {
            return "error/403";
        }

        int subjectId = mark.getSubject().getId();
        markService.deleteMark(id);
        return "redirect:/subjects/" + subjectId + "/details";
    }
}
