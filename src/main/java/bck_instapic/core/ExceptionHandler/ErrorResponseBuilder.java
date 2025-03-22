package bck_instapic.core.ExceptionHandler;

public final class ErrorResponseBuilder {
    private int status;
    private String message;
    private long timestamp;

    public ErrorResponseBuilder withStatus(int status) {
        this.status = status;
        return this;
    }

    public ErrorResponseBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponseBuilder withTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ErrorResponse build() {
        return new ErrorResponse(status, message, timestamp);
    }
}
