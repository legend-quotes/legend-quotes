package com.gangneng.legend_quotes.user.service;

import com.gangneng.legend_quotes.user.dto.request.UserSignUpRequestDTO;
import com.gangneng.legend_quotes.user.dto.response.UserSignUpResponseDTO;
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
}