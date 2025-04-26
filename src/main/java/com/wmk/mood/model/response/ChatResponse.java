package com.wmk.mood.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
}
