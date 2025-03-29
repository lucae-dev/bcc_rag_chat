package bck_instapic.replicate.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReplicatePredictionStatusEnum {
    @JsonProperty("starting")
    STARTING,

    @JsonProperty("processing")
    PROCESSING,

    @JsonProperty("succeeded")
    SUCCEEDED,

    @JsonProperty("failed")
    FAILED,

    @JsonProperty("canceled")
    CANCELED,
}
