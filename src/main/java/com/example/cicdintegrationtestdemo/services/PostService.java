package com.example.cicdintegrationtestdemo.services;

import com.example.cicdintegrationtestdemo.entities.Post;
import com.example.cicdintegrationtestdemo.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    public Long countPosts() {
        return postRepository.count();
    }

    public boolean existsPostById(Long id) {
        return postRepository.existsById(id);
    }
}
