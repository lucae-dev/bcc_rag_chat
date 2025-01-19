package bck_rag_chat.s3.exception;

public class S3BucketNotFoundException extends S3ClientException {
    public S3BucketNotFoundException(String bucketName) {
        super("Bucket not found: " + bucketName);
    }
}
