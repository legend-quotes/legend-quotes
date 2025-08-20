package com.gangneng.legend_quotes.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {
    private String name;
    private String password;
}