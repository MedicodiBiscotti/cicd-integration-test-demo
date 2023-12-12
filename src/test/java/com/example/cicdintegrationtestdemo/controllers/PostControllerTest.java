package com.example.cicdintegrationtestdemo.controllers;

import com.example.cicdintegrationtestdemo.entities.Post;
import com.example.cicdintegrationtestdemo.services.PostService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @Test
    void getAllPosts() throws Exception {
        // Arrange
        List<Post> posts = List.of(
                new Post(1L, "Post 1", "Content 1"),
                new Post(2L, "Post 2", "Content 2")
        );
        Mockito.when(postService.getAllPosts()).thenReturn(posts);

        // Act & Assert
        mvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[0].content").value("Content 1"));
    }

    @Test
    void getPostById() throws Exception {
        // Arrange
        Post post = new Post(1L, "Post 1", "Content 1");
        Mockito.when(postService.getPostById(1L)).thenReturn(post);

        // Act & Assert
        mvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Post 1"))
                .andExpect(jsonPath("$.content").value("Content 1"));
    }

    @Disabled
    @Test
    void getPostById_notFound() throws Exception {
        // Arrange
        Mockito.when(postService.getPostById(1L)).thenThrow();

        // Act & Assert
        mvc.perform(get("/api/posts/1"))
                .andExpect(status().isNotFound());
    }
}