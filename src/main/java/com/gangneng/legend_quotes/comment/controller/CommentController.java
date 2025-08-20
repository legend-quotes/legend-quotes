package com.gangneng.legend_quotes.comment.controller;

import com.gangneng.legend_quotes.comment.dto.request.CommentCreateRequestDTO;
import com.gangneng.legend_quotes.comment.dto.response.CommentResponseDTO;
import com.gangneng.legend_quotes.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDTO requestDTO,
            @CookieValue(value = "userId", required = false) String userIdCookie) {
        
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        CommentResponseDTO responseDTO = commentService.createComment(postId, requestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @CookieValue(value = "userId", required = false) String userIdCookie) {
        
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        boolean deleted = commentService.deleteComment(commentId, userId);
        
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok().build();
    }
}