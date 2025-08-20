package com.gangneng.legend_quotes.post.repository;

import com.gangneng.legend_quotes.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findAllByOrderByCreatedAtDesc();
    
    List<Post> findAllByOrderByCreatedAtAsc();
    
    @Query("SELECT p FROM Post p LEFT JOIN Like l ON p.id = l.postId GROUP BY p.id ORDER BY COUNT(l.id) DESC, p.createdAt DESC")
    List<Post> findAllOrderByLikeCountDesc();
}