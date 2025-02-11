package com.tickets.DTO.Request;

import lombok.Data;

@Data
public class QuestionRequest {
    private Long ticketId;
    private String question;
}
