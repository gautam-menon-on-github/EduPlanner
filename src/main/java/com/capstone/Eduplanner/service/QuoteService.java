package com.capstone.Eduplanner.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class QuoteService {

    private List<String> quotes;

    @PostConstruct
    public void loadQuotes() {
        try {
            quotes = Files.readAllLines(Paths.get("src/main/resources/quotes.txt"));
        } catch (IOException e) {
            quotes = List.of("Keep going!", "You're doing great!");
        }
    }

    public String getRandomQuote() {
        if (quotes == null || quotes.isEmpty())
            return "Stay motivated!";

        return quotes.get(new Random().nextInt(quotes.size()));
    }
}