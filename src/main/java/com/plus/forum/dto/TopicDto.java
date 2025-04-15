package com.plus.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
}
