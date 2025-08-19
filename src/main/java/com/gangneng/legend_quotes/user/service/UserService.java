package com.gangneng.legend_quotes.user.service;

import com.gangneng.legend_quotes.user.dto.request.UserSignUpRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserSignUpResponseDTO;
import com.gangneng.legend_quotes.user.dto.request.UserLoginRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserLoginResponseDTO;
import com.gangneng.legend_quotes.user.entity.User;
import com.gangneng.legend_quotes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserLoginResponseDTO login(UserLoginRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail());
        
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        
        if (user == null || !user.getPassword().equals(requestDTO.getPassword())) {
            responseDTO.setMessage("이메일 또는 비밀번호가 잘못되었습니다.");
            return responseDTO;
        }
        
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setMessage("로그인 성공");
        
        return responseDTO;
    }
}