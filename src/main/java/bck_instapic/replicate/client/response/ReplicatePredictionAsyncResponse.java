package bck_instapic.replicate.client.response;

import bck_instapic.core.LoggableEntity;
import bck_instapic.replicate.client.request.ReplicatePredictionInput;
import bck_instapic.replicate.client.request.ReplicatePredictionUrls;

import java.time.Instant;

public class ReplicatePredictionAsyncResponse<T extends ReplicatePredictionInput> extends LoggableEntity {
    private String id;
    private String model;
    private String version;
    private T input;
    private Object output;
    private String error;
    private ReplicatePredictionStatusEnum status;
    private Instant createdAt;
    private ReplicatePredictionUrls urls;
    private String webhook;
    private String webhook_events_filter;

    public ReplicatePredictionAsyncResponse() {
        // No-args constructor
    }

    public ReplicatePredictionAsyncResponse(
            String id,
            String model,
            String version,
            T input,
            Object output,
            String error,
            ReplicatePredictionStatusEnum status,
            Instant createdAt,
            ReplicatePredictionUrls urls,
            String webhook,
            String webhook_events_filter
    ) {
        this.id = id;
        this.model = model;
        this.version = version;
        this.input = input;
        this.output = output;
        this.error = error;
        this.status = status;
        this.createdAt = createdAt;
        this.urls = urls;
        this.webhook = webhook;
        this.webhook_events_filter = webhook_events_filter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getInput() {
        return input;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ReplicatePredictionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReplicatePredictionStatusEnum status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ReplicatePredictionUrls getUrls() {
        return urls;
    }

    public void setUrls(ReplicatePredictionUrls urls) {
        this.urls = urls;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getWebhook_events_filter() {
        return webhook_events_filter;
    }

    public void setWebhook_events_filter(String webhook_events_filter) {
        this.webhook_events_filter = webhook_events_filter;
    }
}
