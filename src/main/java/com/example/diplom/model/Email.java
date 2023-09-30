package com.example.diplom.model;

import lombok.Data;

@Data
public class Email {
    String to;
    String from;
    String subject;
    String text;
    String template;
}
