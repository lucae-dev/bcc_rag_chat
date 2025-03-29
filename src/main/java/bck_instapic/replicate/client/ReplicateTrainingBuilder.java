package bck_instapic.replicate.client;

import bck_instapic.replicate.client.response.ReplicateTraining;

import java.util.Map;

public final class ReplicateTrainingBuilder {
    private String id;
    private String model;
    private String version;
    private String destination;
    private String status;
    private Map<String, Object> input;
    private Object output;
    private String logs;
    private String error;
    private String createdAt;
    private String startedAt;
    private String completedAt;
    private Map<String, String> urls;

    public ReplicateTrainingBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ReplicateTrainingBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public ReplicateTrainingBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public ReplicateTrainingBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public ReplicateTrainingBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public ReplicateTrainingBuilder withInput(Map<String, Object> input) {
        this.input = input;
        return this;
    }

    public ReplicateTrainingBuilder withOutput(Object output) {
        this.output = output;
        return this;
    }

    public ReplicateTrainingBuilder withLogs(String logs) {
        this.logs = logs;
        return this;
    }

    public ReplicateTrainingBuilder withError(String error) {
        this.error = error;
        return this;
    }

    public ReplicateTrainingBuilder withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ReplicateTrainingBuilder withStartedAt(String startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public ReplicateTrainingBuilder withCompletedAt(String completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    public ReplicateTrainingBuilder withUrls(Map<String, String> urls) {
        this.urls = urls;
        return this;
    }

    public ReplicateTraining build() {
        return new ReplicateTraining(id, model, version, destination, status, input, output, logs, error, createdAt, startedAt, completedAt, urls);
    }
}
