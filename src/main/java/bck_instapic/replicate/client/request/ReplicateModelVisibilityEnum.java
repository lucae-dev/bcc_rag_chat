package bck_instapic.replicate.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReplicateModelVisibilityEnum {
    @JsonProperty("public")
    PUBLIC,
    @JsonProperty("private")
    PRIVATE
}
