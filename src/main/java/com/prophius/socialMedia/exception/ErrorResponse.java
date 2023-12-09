package com.prophius.socialMedia.exception;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private List<String> errors;

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
