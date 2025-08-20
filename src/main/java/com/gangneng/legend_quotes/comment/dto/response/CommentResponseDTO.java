package com.gangneng.legend_quotes.comment.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDTO {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;
}