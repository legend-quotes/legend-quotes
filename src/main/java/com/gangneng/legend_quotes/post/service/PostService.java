package com.gangneng.legend_quotes.post.service;

import com.gangneng.legend_quotes.post.dto.request.PostCreateRequestDTO;
import com.gangneng.legend_quotes.post.dto.response.PostCreateResponseDTO;
import com.gangneng.legend_quotes.post.entity.Post;
import com.gangneng.legend_quotes.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostCreateResponseDTO createPost(PostCreateRequestDTO requestDTO, Long userId) {
        Post post = new Post();
        post.setTitle(requestDTO.getTitle());
        post.setContent(requestDTO.getContent());
        post.setUserId(userId);
        post.setProfessor(requestDTO.getProfessor());

        Post savedPost = postRepository.save(post);

        PostCreateResponseDTO responseDTO = new PostCreateResponseDTO();
        responseDTO.setId(savedPost.getId());
        responseDTO.setTitle(savedPost.getTitle());
        responseDTO.setContent(savedPost.getContent());
        responseDTO.setUserId(savedPost.getUserId());
        responseDTO.setProfessor(savedPost.getProfessor());
        responseDTO.setCreatedAt(savedPost.getCreatedAt());

        return responseDTO;
    }
}