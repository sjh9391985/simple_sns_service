package com.example.sns.controller;

import com.example.sns.controller.request.PostCreateRequest;
import com.example.sns.controller.response.PostResponse;
import com.example.sns.controller.response.Response;
import com.example.sns.model.Post;
import com.example.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {

        postService.create(request.getTitle(), request.getBody(), authentication.getName());

        return Response.success();

    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Integer postid, @RequestBody PostCreateRequest request, Authentication authentication) {

        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postid);

        return Response.success(PostResponse.fromPost(post));

    }


}
