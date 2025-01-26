package bck_instapic.s3.service;

import bck_instapic.s3.exception.S3BucketNotFoundException;
import bck_instapic.s3.exception.S3ClientException;
import bck_instapic.s3.exception.S3ObjectNotFoundException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public void createBucket(String bucketName) {
        try {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to create bucket: " + bucketName, e);
        }
    }

    public void deleteBucket(String bucketName) {
        try {
            s3Client.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            throw new S3BucketNotFoundException(bucketName);
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to delete bucket: " + bucketName, e);
        }
    }

    public List<String> listBuckets() {
        try {
            return s3Client.listBuckets().buckets().stream()
                    .map(Bucket::name)
                    .collect(Collectors.toList());
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to list buckets", e);
        }
    }

    public void uploadObject(String bucketName, String key, InputStream data, long contentLength, String contentType) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentLength(contentLength)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromInputStream(data, contentLength)
            );
        } catch (NoSuchBucketException e) {
            throw new S3BucketNotFoundException(bucketName);
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to upload object to " + bucketName + "/" + key, e);
        }
    }

    public ResponseInputStream<GetObjectResponse> downloadObject(String bucketName, String key) {
        try {
            return s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build());
        } catch (NoSuchKeyException e) {
            throw new S3ObjectNotFoundException(key, bucketName);
        } catch (NoSuchBucketException e) {
            throw new S3BucketNotFoundException(bucketName);
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to download object: " + bucketName + "/" + key, e);
        }
    }

    public void deleteObject(String bucketName, String key) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
        } catch (NoSuchKeyException e) {
            throw new S3ObjectNotFoundException(key, bucketName);
        } catch (NoSuchBucketException e) {
            throw new S3BucketNotFoundException(bucketName);
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to delete object: " + bucketName + "/" + key, e);
        }
    }

    public List<S3Object> listObjects(String bucketName, String prefix) {
        try {
            ListObjectsV2Response response = s3Client.listObjectsV2(
                    ListObjectsV2Request.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .build());
            return response.contents();
        } catch (NoSuchBucketException e) {
            throw new S3BucketNotFoundException(bucketName);
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to list objects in bucket: " + bucketName, e);
        }
    }

    public URL generatePresignedUrl(String bucketName, String key, long expirationInSeconds) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofSeconds(expirationInSeconds))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url();
        } catch (NoSuchBucketException e) {
            throw new S3BucketNotFoundException(bucketName);
        } catch (NoSuchKeyException e) {
            throw new S3ObjectNotFoundException(key, bucketName);
        } catch (S3Exception e) {
            throw new S3ClientException("Failed to generate presigned URL for: " + bucketName + "/" + key, e);
        }
    }
}
