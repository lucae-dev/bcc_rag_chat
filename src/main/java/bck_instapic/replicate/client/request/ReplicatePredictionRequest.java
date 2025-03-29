package bck_instapic.replicate.client.request;

import bck_instapic.core.LoggableEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;

public class ReplicatePredictionRequest<T extends ReplicatePredictionInput> extends LoggableEntity {
    private final String version;
    private final T input;
    private final String webhook;
    private final String webhook_events_filter;

    public ReplicatePredictionRequest(String version, T input, String webhook, String webhook_events_filter) {
        this.version = version;
        this.input = input;
        this.webhook = webhook;
        this.webhook_events_filter = webhook_events_filter;
    }

    public String getVersion() {
        return version;
    }

    public T getInput() {
        return input;
    }

    public String getWebhook() {
        return webhook;
    }

    public String getWebhook_events_filter() {
        return webhook_events_filter;
    }
}
