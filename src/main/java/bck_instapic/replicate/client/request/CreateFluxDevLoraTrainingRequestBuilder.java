package bck_instapic.replicate.client.request;

import java.util.List;

public final class CreateFluxDevLoraTrainingRequestBuilder {
    private FluxDevLoraTrainerInput input;
    private String destination;
    private String webhook;
    private String webhookCompleted;
    private List<String> webhookEventsFilter;

    public CreateFluxDevLoraTrainingRequestBuilder withInput(FluxDevLoraTrainerInput input) {
        this.input = input;
        return this;
    }

    public CreateFluxDevLoraTrainingRequestBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public CreateFluxDevLoraTrainingRequestBuilder withWebhook(String webhook) {
        this.webhook = webhook;
        return this;
    }

    public CreateFluxDevLoraTrainingRequestBuilder withWebhookCompleted(String webhookCompleted) {
        this.webhookCompleted = webhookCompleted;
        return this;
    }

    public CreateFluxDevLoraTrainingRequestBuilder withWebhookEventsFilter(List<String> webhookEventsFilter) {
        this.webhookEventsFilter = webhookEventsFilter;
        return this;
    }

    public CreateFluxDevLoraTrainingRequest build() {
        return new CreateFluxDevLoraTrainingRequest(destination, webhook, webhookCompleted, webhookEventsFilter, input);
    }
}
