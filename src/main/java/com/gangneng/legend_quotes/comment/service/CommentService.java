package com.gangneng.legend_quotes.comment.service;

import com.gangneng.legend_quotes.comment.dto.request.CommentCreateRequestDTO;
import com.gangneng.legend_quotes.comment.dto.response.CommentResponseDTO;
import com.gangneng.legend_quotes.comment.entity.Comment;
import com.gangneng.legend_quotes.comment.repository.CommentRepository;
import com.gangneng.legend_quotes.user.entity.User;
import com.gangneng.legend_quotes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponseDTO createComment(Long postId, CommentCreateRequestDTO requestDTO, Long userId) {
        Comment comment = new Comment();
        comment.setContent(requestDTO.getContent());
        comment.setPostId(postId);
        comment.setUserId(userId);
        
        Comment savedComment = commentRepository.save(comment);
        User user = userRepository.findById(userId).orElse(null);
        
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(savedComment.getId());
        responseDTO.setContent(savedComment.getContent());
        responseDTO.setUserId(savedComment.getUserId());
        responseDTO.setUserName(user != null ? user.getName() : "알 수 없음");
        responseDTO.setCreatedAt(savedComment.getCreatedAt());
        
        return responseDTO;
    }

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        
        return comments.stream()
                .map(comment -> {
                    User user = userRepository.findById(comment.getUserId()).orElse(null);
                    CommentResponseDTO dto = new CommentResponseDTO();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setUserId(comment.getUserId());
                    dto.setUserName(user != null ? user.getName() : "알 수 없음");
                    dto.setCreatedAt(comment.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public boolean deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        
        if (comment == null || !comment.getUserId().equals(userId)) {
            return false;
        }
        
        commentRepository.delete(comment);
        return true;
    }
}