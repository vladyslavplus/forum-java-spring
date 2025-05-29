package com.plus.forum.services;

import com.plus.forum.repositories.Topic;
import com.plus.forum.repositories.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getAllTopics(String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return topicRepository.findAllByOrderByCreatedAtAsc();
        } else {
            return topicRepository.findAllByOrderByCreatedAtDesc();
        }
    }

    public Topic getTopicById(Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.orElse(null);
    }

    public List<Topic> searchTopicsByTitle(String keyword, String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return topicRepository.findByTitleContainingIgnoreCaseOrderByCreatedAtAsc(keyword);
        } else {
            return topicRepository.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword);
        }
    }

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public void updateTopic(Long id, Topic newTopic) {
        Topic oldTopic = getTopicById(id);
        if(oldTopic != null) {
            oldTopic.setTitle(newTopic.getTitle());
            oldTopic.setDescription(newTopic.getDescription());
            oldTopic.setContent(newTopic.getContent());
            oldTopic.setCategory(newTopic.getCategory());

            topicRepository.save(oldTopic);
        }
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
