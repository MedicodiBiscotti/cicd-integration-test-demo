package com.example.cicdintegrationtestdemo.services;

import com.example.cicdintegrationtestdemo.entities.Post;
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
class PostServiceTest {
    private static Post post1;
    private static Post post2;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService.deleteAllPosts();

        post1 = new Post("Post 1", "Content 1");
        post2 = new Post("Post 2", "Content 2");
        postService.savePost(post1);
        postService.savePost(post2);
    }

    @Test
    void getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        assertEquals(2, posts.size());
    }
}