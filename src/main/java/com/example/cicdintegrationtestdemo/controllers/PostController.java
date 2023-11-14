package com.example.cicdintegrationtestdemo.controllers;

import com.example.cicdintegrationtestdemo.entities.Post;
import com.example.cicdintegrationtestdemo.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("count")
    public Long countPosts() {
        return postService.countPosts();
    }

    @PostMapping()
    public Post savePost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @DeleteMapping("{id}")
    public void deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
    }

    @DeleteMapping()
    public void deleteAllPosts() {
        postService.deleteAllPosts();
    }

    // TODO: PUT
}
