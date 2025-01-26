package bck_instapic.s3.exception;

public class S3ObjectNotFoundException extends S3ClientException {
    public S3ObjectNotFoundException(String key, String bucketName) {
        super("Object not found: " + key + " in bucket: " + bucketName);
    }
}