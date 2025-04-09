package com.plus.forum.services;

import org.springframework.stereotype.Service;

@Service
public class TextUtilsService {
    public String createSnippet(String content, int maxWords) {
        if (content == null || content.isBlank()) {
            return "";
        }

        String[] words = content.trim().split("\\s+");
        if (words.length <= maxWords) {
            return content;
        }

        StringBuilder snippet = new StringBuilder();
        for (int i = 0; i < maxWords; i++) {
            snippet.append(words[i]).append(" ");
        }
        snippet.append("...");
        return snippet.toString().trim();
    }
}
