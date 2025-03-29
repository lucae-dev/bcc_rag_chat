package bck_instapic.replicate.client.request;

import bck_instapic.core.LoggableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a request body to create a new training.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTrainingRequest extends LoggableEntity {

    /**
     * The desired model to push to in the format `{owner}/{model_name}`.
     */
    private String destination;

    /**
     * The URL to send a POST request when the training is completed or for updates.
     */
    private String webhook;

    @JsonProperty("webhook_completed")
    private String webhookCompleted;

    @JsonProperty("webhook_events_filter")
    private List<String> webhookEventsFilter;

    public CreateTrainingRequest() {
    }

    public CreateTrainingRequest(String destination, String webhook, String webhookCompleted, List<String> webhookEventsFilter) {
        this.destination = destination;
        this.webhook = webhook;
        this.webhookCompleted = webhookCompleted;
        this.webhookEventsFilter = webhookEventsFilter;
    }

    public String getDestination() {
        return destination;
    }

    public String getWebhook() {
        return webhook;
    }

    public String getWebhookCompleted() {
        return webhookCompleted;
    }

    public List<String> getWebhookEventsFilter() {
        return webhookEventsFilter;
    }
}