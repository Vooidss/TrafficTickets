package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "blackListTokens")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackListTokens {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token", nullable = false)
    @Size(max = 400)
    private String token;
}
