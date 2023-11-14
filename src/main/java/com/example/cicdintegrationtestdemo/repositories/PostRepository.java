package com.example.cicdintegrationtestdemo.repositories;

import com.example.cicdintegrationtestdemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}