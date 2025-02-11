package com.tickets.Services;

import com.tickets.Models.Answers;
import com.tickets.Models.Questions;
import com.tickets.Repositories.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void save(Questions question) {
        for (Answers a : question.getAnswers()) {
            a.setQuestion(question);
            answerRepository.save(a);
        }
    }
}
