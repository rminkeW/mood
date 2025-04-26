package com.wmk.mood.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String avatarUrl;
}
