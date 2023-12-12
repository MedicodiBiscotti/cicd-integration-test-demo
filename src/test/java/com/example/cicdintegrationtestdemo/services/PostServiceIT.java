package com.example.cicdintegrationtestdemo.services;

import com.example.cicdintegrationtestdemo.entities.Post;
import com.example.cicdintegrationtestdemo.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @DataJpaTest does not load the full application context, so it won't load the PostService bean.
// That's why we use @SpringBootTest instead.
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostServiceIT {
    private static Post post1;
    private static Post post2;
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postService.deleteAllPosts();

        post1 = new Post("Post 1", "Content 1");
        post2 = new Post("Post 2", "Content 2");
        postRepository.saveAll(List.of(post1, post2));
    }

    @Test
    void getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        assertEquals(2, posts.size());
    }
}