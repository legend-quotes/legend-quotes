package com.gangneng.legend_quotes.post.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostDetailResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String professor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likeCount;
    private boolean isLiked;
}