package com.gangneng.legend_quotes.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String message;
}