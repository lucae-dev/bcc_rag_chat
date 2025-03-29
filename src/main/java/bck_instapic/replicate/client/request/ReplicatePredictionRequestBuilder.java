package bck_instapic.replicate.client.request;

public final class ReplicatePredictionRequestBuilder {
    private String version;
    private ReplicatePredictionInput input;
    private String webhook;
    private String webhook_events_filter;

    public ReplicatePredictionRequestBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public ReplicatePredictionRequestBuilder withInput(ReplicatePredictionInput input) {
        this.input = input;
        return this;
    }

    public ReplicatePredictionRequestBuilder withWebhook(String webhook) {
        this.webhook = webhook;
        return this;
    }

    public ReplicatePredictionRequestBuilder withWebhook_events_filter(String webhook_events_filter) {
        this.webhook_events_filter = webhook_events_filter;
        return this;
    }

    public ReplicatePredictionRequest build() {
        return new ReplicatePredictionRequest(version, input, webhook, webhook_events_filter);
    }
}
