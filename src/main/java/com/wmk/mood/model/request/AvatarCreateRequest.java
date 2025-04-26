package com.wmk.mood.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AvatarCreateRequest implements Serializable{
    private static final long serialVersionUID = 1L;
    private String avatarName;
    private String character;
}