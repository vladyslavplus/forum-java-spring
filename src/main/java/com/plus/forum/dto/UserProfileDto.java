package com.plus.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserProfileDto {
    private String username;
    private String avatar;
    private List<TopicDto> topics;
}
