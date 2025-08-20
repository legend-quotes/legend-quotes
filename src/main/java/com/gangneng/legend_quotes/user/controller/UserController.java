package com.gangneng.legend_quotes.user.controller;

import com.gangneng.legend_quotes.user.dto.request.UserSignUpRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserSignUpResponseDTO;
import com.gangneng.legend_quotes.user.dto.request.UserLoginRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserLoginResponseDTO;
import com.gangneng.legend_quotes.user.dto.request.UserUpdateRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserUpdateResponseDTO;
import com.gangneng.legend_quotes.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

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
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO, HttpServletResponse response) {
        UserLoginResponseDTO responseDTO = userService.login(requestDTO, response);
        
        if (responseDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
        
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserUpdateResponseDTO> updateProfile(
            @RequestBody UserUpdateRequestDTO requestDTO,
            @CookieValue(value = "userId", required = false) String userIdCookie) {
        
        return userService.updateUserProfile(requestDTO, userIdCookie);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserUpdateResponseDTO> getProfile(
            @CookieValue(value = "userId", required = false) String userIdCookie) {
        
        return userService.getUserProfile(userIdCookie);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile(
            @CookieValue(value = "userId", required = false) String userIdCookie,
            HttpServletResponse response) {
        
        return userService.deleteUserProfile(userIdCookie, response);
    }
}
