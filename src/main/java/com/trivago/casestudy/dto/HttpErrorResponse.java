package com.trivago.casestudy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class HttpErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "Europe/Berlin")
    private Date timeStamp;
    private HttpStatus httpStatus;
    private String message;

    public HttpErrorResponse(HttpStatus httpStatus, String message) {
        this.timeStamp = new Date();
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
