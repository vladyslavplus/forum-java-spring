package com.plus.forum.dto;

import com.plus.forum.repositories.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecentCommentDto {
    private final Comment comment;
    private final String formattedTimeAgo;
    private final String commentSnippet;
}
