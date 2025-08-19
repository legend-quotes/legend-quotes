package com.gangneng.legend_quotes.post.repository;

import com.gangneng.legend_quotes.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}