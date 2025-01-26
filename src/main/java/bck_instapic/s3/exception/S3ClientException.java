package bck_instapic.s3.exception;

public class S3ClientException extends RuntimeException {
    public S3ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public S3ClientException(String message) {
        super(message);
    }
}
