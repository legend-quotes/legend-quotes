package com.gangneng.legend_quotes.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {

    private String email;
    private String password;
}