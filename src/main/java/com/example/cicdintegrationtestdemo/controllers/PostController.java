package com.example.cicdintegrationtestdemo.controllers;

import com.example.cicdintegrationtestdemo.entities.Post;
import com.example.cicdintegrationtestdemo.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String getAllPosts() {
        return postService.getAllPosts().toString();
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable Long id) {
        return postService.getPostById(id).toString();
    }

    @GetMapping("/posts/count")
    public Long countPosts() {
        return postService.countPosts();
    }

    @PostMapping("/posts")
    public Post savePost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
    }

    @DeleteMapping("/posts")
    public void deleteAllPosts() {
        postService.deleteAllPosts();
    }

    // TODO: PUT
}
