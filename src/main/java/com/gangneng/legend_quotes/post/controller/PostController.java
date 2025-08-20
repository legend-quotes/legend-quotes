package com.gangneng.legend_quotes.post.controller;

import com.gangneng.legend_quotes.post.dto.request.PostCreateRequestDTO;
import com.gangneng.legend_quotes.post.dto.request.PostUpdateRequestDTO;
import com.gangneng.legend_quotes.post.dto.response.PostCreateResponseDTO;
import com.gangneng.legend_quotes.post.dto.response.PostListResponseDTO;
import com.gangneng.legend_quotes.post.dto.response.PostDetailResponseDTO;
import java.util.List;
import com.gangneng.legend_quotes.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostCreateResponseDTO> createPost(
            @RequestBody PostCreateRequestDTO requestDTO,
            @CookieValue(value = "userId", required = false) String userIdCookie,
            @RequestHeader(value = "User-Id", required = false) String userIdHeader) {
        
        String userIdStr = userIdCookie != null ? userIdCookie : userIdHeader;
        
        if (userIdStr == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdStr);
        PostCreateResponseDTO responseDTO = postService.createPost(requestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<PostListResponseDTO>> getAllPosts() {
        List<PostListResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDTO> getPostById(@PathVariable Long postId) {
        PostDetailResponseDTO post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDTO> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDTO requestDTO,
            @CookieValue(value = "userId", required = false) String userIdCookie) {
        
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        PostDetailResponseDTO responseDTO = postService.updatePost(postId, requestDTO, userId);
        
        if (responseDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(responseDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @CookieValue(value = "userId", required = false) String userIdCookie) {
        
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        boolean deleted = postService.deletePost(postId, userId);
        
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok().build();
    }
}