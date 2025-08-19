package com.gangneng.legend_quotes.user.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserSignUpResponseDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}