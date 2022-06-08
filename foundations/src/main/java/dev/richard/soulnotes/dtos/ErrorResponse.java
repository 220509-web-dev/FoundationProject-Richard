package dev.richard.soulnotes.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
public class ErrorResponse {

    private int statusCode;
    private String message;
    private String timestamp;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
    public String generateErrors(ObjectMapper mapper) throws JsonProcessingException {
        ObjectNode nodes = mapper.createObjectNode();
        nodes.put("code", this.getStatusCode());
        nodes.put("message", this.getMessage());
        nodes.put("timestamp", this.getTimestamp());
        return mapper.writeValueAsString(nodes);
    }
}
