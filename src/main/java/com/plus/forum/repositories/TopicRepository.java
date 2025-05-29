package com.plus.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByOrderByCreatedAtAsc();
    List<Topic> findAllByOrderByCreatedAtDesc();
    List<Topic> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);
    List<Topic> findByTitleContainingIgnoreCaseOrderByCreatedAtAsc(String keyword);
}
