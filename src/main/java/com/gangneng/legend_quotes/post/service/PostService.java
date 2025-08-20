package com.gangneng.legend_quotes.post.service;

import com.gangneng.legend_quotes.post.dto.request.PostCreateRequestDTO;
import com.gangneng.legend_quotes.post.dto.request.PostUpdateRequestDTO;
import com.gangneng.legend_quotes.post.dto.response.PostCreateResponseDTO;
import com.gangneng.legend_quotes.post.dto.response.PostListResponseDTO;
import com.gangneng.legend_quotes.post.dto.response.PostDetailResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import com.gangneng.legend_quotes.post.entity.Post;
import com.gangneng.legend_quotes.post.repository.PostRepository;
import com.gangneng.legend_quotes.like.entity.Like;
import com.gangneng.legend_quotes.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

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

    @Transactional(readOnly = true)
    public List<PostListResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        
        return posts.stream()
                .map(post -> {
                    PostListResponseDTO dto = new PostListResponseDTO();
                    dto.setId(post.getId());
                    dto.setTitle(post.getTitle());
                    dto.setContent(post.getContent());
                    dto.setUserId(post.getUserId());
                    dto.setProfessor(post.getProfessor());
                    dto.setCreatedAt(post.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        
        PostDetailResponseDTO dto = new PostDetailResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUserId(post.getUserId());
        dto.setProfessor(post.getProfessor());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setLikeCount(likeRepository.countByPostId(postId));
        
        return dto;
    }

    public PostDetailResponseDTO getPostByIdWithUser(Long postId, Long userId) {
        PostDetailResponseDTO dto = getPostById(postId);
        if (userId != null) {
            dto.setLiked(likeRepository.existsByPostIdAndUserId(postId, userId));
        }
        return dto;
    }

    public PostDetailResponseDTO updatePost(Long postId, PostUpdateRequestDTO requestDTO, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        
        if (post == null || !post.getUserId().equals(userId)) {
            return null;
        }
        
        post.setTitle(requestDTO.getTitle());
        post.setContent(requestDTO.getContent());
        post.setProfessor(requestDTO.getProfessor());
        
        Post updatedPost = postRepository.save(post);
        
        PostDetailResponseDTO dto = new PostDetailResponseDTO();
        dto.setId(updatedPost.getId());
        dto.setTitle(updatedPost.getTitle());
        dto.setContent(updatedPost.getContent());
        dto.setUserId(updatedPost.getUserId());
        dto.setProfessor(updatedPost.getProfessor());
        dto.setCreatedAt(updatedPost.getCreatedAt());
        dto.setUpdatedAt(updatedPost.getUpdatedAt());
        
        return dto;
    }

    public boolean deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        
        if (post == null || !post.getUserId().equals(userId)) {
            return false;
        }
        
        postRepository.delete(post);
        return true;
    }

    public boolean toggleLike(Long postId, Long userId) {
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            likeRepository.deleteByPostIdAndUserId(postId, userId);
            return false;
        } else {
            Like like = new Like();
            like.setPostId(postId);
            like.setUserId(userId);
            likeRepository.save(like);
            return true;
        }
    }
}