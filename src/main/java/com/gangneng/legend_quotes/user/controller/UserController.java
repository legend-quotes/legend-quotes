package com.gangneng.legend_quotes.user.controller;

import com.gangneng.legend_quotes.user.dto.request.UserSignUpRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserSignUpResponseDTO;
import com.gangneng.legend_quotes.user.dto.request.UserLoginRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserLoginResponseDTO;
import com.gangneng.legend_quotes.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> signUp(@RequestBody UserSignUpRequestDTO requestDTO) {
        UserSignUpResponseDTO responseDTO = userService.signUp(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO) {
        UserLoginResponseDTO responseDTO = userService.login(requestDTO);
        
        if (responseDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
        
        return ResponseEntity.ok(responseDTO);
    }
}
