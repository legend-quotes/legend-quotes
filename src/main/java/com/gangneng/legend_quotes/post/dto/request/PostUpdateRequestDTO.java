package com.gangneng.legend_quotes.post.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequestDTO {
    private String title;
    private String content;
    private String professor;
}