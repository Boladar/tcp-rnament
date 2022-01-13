package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Question {
    private String text;
    private Boolean correctAnswer;
}
