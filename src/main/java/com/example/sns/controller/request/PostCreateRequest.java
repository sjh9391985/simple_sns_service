package com.example.sns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCreateRequest {

    private String title;
    private String body;

}
