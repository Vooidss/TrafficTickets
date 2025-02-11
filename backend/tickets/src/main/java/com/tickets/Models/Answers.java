package com.tickets.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "answers")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Questions question;

    @Column(name = "is_correct", nullable = false)
    @NotNull
    private Boolean isCorrect;
}
