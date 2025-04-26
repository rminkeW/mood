package com.wmk.mood.model.request;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class emotionCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String emotion;
    private String recordTime;
}
