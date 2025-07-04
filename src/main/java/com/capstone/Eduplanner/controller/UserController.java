package com.capstone.Eduplanner.controller;

import com.capstone.Eduplanner.model.Mark;
import com.capstone.Eduplanner.model.Subject;
import com.capstone.Eduplanner.model.User;
import com.capstone.Eduplanner.service.MarkService;
import com.capstone.Eduplanner.service.QuoteService;
import com.capstone.Eduplanner.service.UserService;
import com.capstone.Eduplanner.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MarkService markService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.register(user);
        model.addAttribute("message", "Registration successful! Proceed with login!");
        return "login";
    }

    @GetMapping({"/", "/login"})
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        Optional<User> currentUser = userService.login(user.getEmail(), user.getPassword());
        if (currentUser.isPresent()) {
            session.setAttribute("userId", currentUser.get().getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @Autowired
    private QuoteService quoteService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        String currentUsername = userService.findById(userId).get().getName();
        model.addAttribute("name", currentUsername);

        String quote = quoteService.getRandomQuote();
        model.addAttribute("quote", quote);

        return "dashboard";
    }

    @GetMapping("/performance")
    public String showPerformanceChart(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) return "redirect:/login";

        User user = optionalUser.get();
        List<Subject> subjects = subjectService.getSubjectsByUserId(user.getId());

        Map<String, Double> performanceMap = new LinkedHashMap<>();
        for (Subject subject : subjects) {
            List<Mark> marks = markService.getMarksBySubject(subject);
            double avg = marks.stream()
                    .mapToDouble(mark -> mark.getScore())
                    .average()
                    .orElse(0.0);
            performanceMap.put(subject.getName(), avg);
        }

        model.addAttribute("performanceData", performanceMap);
        return "performance";
    }

}
