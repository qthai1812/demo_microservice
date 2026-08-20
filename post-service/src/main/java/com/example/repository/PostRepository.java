package com.example.repository;

import com.example.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface PostRepository extends MongoRepository<Post,String> {
    Page<Post> findAllByUserId(String userId, Pageable pageable);
}
