package bck_instapic.replicate.client.request;

import bck_instapic.core.LoggableEntity;

public class CreateReplicateModelRequest extends LoggableEntity {
    private String name;
    private String owner;
    private String description;
    private ReplicateModelVisibilityEnum visibility;

    public CreateReplicateModelRequest(String name, String owner, String description, ReplicateModelVisibilityEnum visibility) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public ReplicateModelVisibilityEnum getVisibility() {
        return visibility;
    }
}
