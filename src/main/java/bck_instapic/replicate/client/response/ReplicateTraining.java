package bck_instapic.replicate.client.response;

import bck_instapic.core.LoggableEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Represents a training job on Replicate.
 *
 * Based on the Python snippet's "Training" class fields.
 */
public class ReplicateTraining extends LoggableEntity {
    /**
     * The unique ID of the training.
     */
    private String id;

    /**
     * An identifier for the model used to create the training, in the form `owner/name`.
     */
    private String model;

    /**
     * The version of the model used to create the training.
     */
    private String version;

    /**
     * The model destination of the training, e.g. `owner/new-model-name`.
     */
    private String destination;

    /**
     * The status of the training:
     * "starting", "processing", "succeeded", "failed", or "canceled".
     */
    private String status;

    /**
     * The input to the training (arbitrary key-value).
     */
    private Map<String, Object> input;

    /**
     * The output of the training, if any (arbitrary type).
     */
    private Object output;

    /**
     * The logs of the training, if available (plaintext).
     */
    private String logs;

    /**
     * The error encountered during the training, if any.
     */
    private String error;

    /**
     * When the training was created (ISO8601 datetime).
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * When the training was started (ISO8601 datetime).
     */
    @JsonProperty("started_at")
    private String startedAt;

    /**
     * When the training was completed (ISO8601 datetime), if finished.
     */
    @JsonProperty("completed_at")
    private String completedAt;

    /**
     * URLs associated with the training (like GET, cancel).
     */
    private Map<String, String> urls;

    public ReplicateTraining() {
    }

    public ReplicateTraining(String id, String model, String version, String destination, String status, Map<String, Object> input, Object output, String logs, String error, String createdAt, String startedAt, String completedAt, Map<String, String> urls) {
        this.id = id;
        this.model = model;
        this.version = version;
        this.destination = destination;
        this.status = status;
        this.input = input;
        this.output = output;
        this.logs = logs;
        this.error = error;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getVersion() {
        return version;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public Object getOutput() {
        return output;
    }

    public String getLogs() {
        return logs;
    }

    public String getError() {
        return error;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public Map<String, String> getUrls() {
        return urls;
    }
}