package com.example.cicdintegrationtestdemo.services;

import com.example.cicdintegrationtestdemo.entities.Post;
import com.example.cicdintegrationtestdemo.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PostService.class)
public class PostServiceTest {
    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    void getAllPosts() {
        // Arrange
        List<Post> posts = List.of(
                new Post(1L, "Post 1", "Content 1"),
                new Post(2L, "Post 2", "Content 2")
        );
        when(postRepository.findAll()).thenReturn(posts);

        // Act
        List<Post> foundPosts = postService.getAllPosts();

        // Assert
        assertEquals(2, foundPosts.size());
    }

    @Test
    void getPostById() {
        // Arrange
        Post post = new Post(1L, "Post 1", "Content 1");
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // Act
        Post foundPost = postService.getPostById(1L);

        // Assert
        assertEquals(post, foundPost);
    }

    @Test
    void givenBadId_whenGetPostById_thenThrowNoSuchElementException() {
        // Arrange
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> postService.getPostById(1L));
    }
}
