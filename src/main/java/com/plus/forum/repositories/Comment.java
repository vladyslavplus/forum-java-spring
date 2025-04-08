package com.plus.forum.repositories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
