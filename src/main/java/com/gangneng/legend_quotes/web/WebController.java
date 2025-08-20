package com.gangneng.legend_quotes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/posts")
    public String posts() {
        return "posts";
    }

    @GetMapping("/posts/create")
    public String createPost(@CookieValue(value = "userId", required = false) String userIdCookie) {
        if (userIdCookie == null) {
            return "redirect:/login";
        }
        return "post-create";
    }

    @GetMapping("/posts/{postId}")
    public String postDetail() {
        return "post-detail";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost() {
        return "post-edit";
    }
}