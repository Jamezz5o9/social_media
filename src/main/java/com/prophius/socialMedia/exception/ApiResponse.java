package com.prophius.socialMedia.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private boolean success;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    private Object data;

    private int statusCode;

    public ApiResponse(boolean success, Object data){
        this.success = success;
        this.data = data;
    }
}
