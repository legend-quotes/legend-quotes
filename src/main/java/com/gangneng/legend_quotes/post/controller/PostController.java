package com.gangneng.legend_quotes.post.controller;

import com.gangneng.legend_quotes.post.dto.request.PostCreateRequestDTO;
import com.gangneng.legend_quotes.post.dto.response.PostCreateResponseDTO;
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
}