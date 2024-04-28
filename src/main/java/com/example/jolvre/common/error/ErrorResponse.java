package com.example.jolvre.common.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ErrorResponse {
    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    private final int status;
    private final String message;
    private final String error;
    private final String path;

    public ErrorResponse(int status, String message, String path, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
    }

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(status.value(), message, path, status.toString());
    }

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
