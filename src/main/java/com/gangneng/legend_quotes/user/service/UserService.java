package com.gangneng.legend_quotes.user.service;

import com.gangneng.legend_quotes.user.dto.request.UserSignUpRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserSignUpResponseDTO;
import com.gangneng.legend_quotes.user.dto.request.UserLoginRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserLoginResponseDTO;
import com.gangneng.legend_quotes.user.dto.request.UserUpdateRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserUpdateResponseDTO;
import com.gangneng.legend_quotes.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.gangneng.legend_quotes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());

        User savedUser = userRepository.save(user);

        UserSignUpResponseDTO responseDTO = new UserSignUpResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setCreatedAt(savedUser.getCreatedAt());

        return responseDTO;
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO requestDTO, HttpServletResponse response) {
        User user = userRepository.findByEmail(requestDTO.getEmail());
        
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        
        if (user == null || !user.getPassword().equals(requestDTO.getPassword())) {
            responseDTO.setMessage("이메일 또는 비밀번호가 잘못되었습니다.");
            return responseDTO;
        }
        
        Cookie userIdCookie = new Cookie("userId", user.getId().toString());
        userIdCookie.setMaxAge(5 * 60 * 60);
        userIdCookie.setHttpOnly(true);
        userIdCookie.setPath("/");
        response.addCookie(userIdCookie);
        
        Cookie loginStatusCookie = new Cookie("isLoggedIn", "true");
        loginStatusCookie.setMaxAge(5 * 60 * 60);
        loginStatusCookie.setPath("/");
        response.addCookie(loginStatusCookie);
        
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setMessage("로그인 성공");
        
        return responseDTO;
    }

    public UserUpdateResponseDTO updateUser(Long userId, UserUpdateRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElse(null);
        
        UserUpdateResponseDTO responseDTO = new UserUpdateResponseDTO();
        
        if (user == null) {
            responseDTO.setMessage("사용자를 찾을 수 없습니다.");
            return responseDTO;
        }
        
        if (requestDTO.getName() != null && !requestDTO.getName().trim().isEmpty()) {
            user.setName(requestDTO.getName());
        }
        
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().trim().isEmpty()) {
            user.setPassword(requestDTO.getPassword());
        }
        
        User updatedUser = userRepository.save(user);
        
        responseDTO.setId(updatedUser.getId());
        responseDTO.setName(updatedUser.getName());
        responseDTO.setEmail(updatedUser.getEmail());
        responseDTO.setMessage("회원 정보가 수정되었습니다.");
        
        return responseDTO;
    }

    public ResponseEntity<UserUpdateResponseDTO> updateUserProfile(UserUpdateRequestDTO requestDTO, String userIdCookie) {
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        UserUpdateResponseDTO responseDTO = updateUser(userId, requestDTO);
        
        if (responseDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
        
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<UserUpdateResponseDTO> getUserProfile(String userIdCookie) {
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        User user = userRepository.findById(userId).orElse(null);
        
        UserUpdateResponseDTO responseDTO = new UserUpdateResponseDTO();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> deleteUserProfile(String userIdCookie, HttpServletResponse response) {
        if (userIdCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Long userId = Long.parseLong(userIdCookie);
        User user = userRepository.findById(userId).orElse(null);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        userRepository.delete(user);
        
        Cookie userIdCookie2 = new Cookie("userId", "");
        userIdCookie2.setMaxAge(0);
        userIdCookie2.setPath("/");
        response.addCookie(userIdCookie2);
        
        Cookie loginStatusCookie = new Cookie("isLoggedIn", "");
        loginStatusCookie.setMaxAge(0);
        loginStatusCookie.setPath("/");
        response.addCookie(loginStatusCookie);
        
        return ResponseEntity.ok().build();
    }
}