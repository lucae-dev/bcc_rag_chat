package bck_instapic.core.ExceptionHandler;

import bck_instapic.core.LoggableEntity;

public class ErrorResponse extends LoggableEntity {
    private int status;
    private String message;
    private long timestamp;

    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}