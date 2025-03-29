package bck_instapic.replicate.client.request;

public final class CreateReplicateModelRequestBuilder {
    private String name;
    private String owner;
    private String description;
    private ReplicateModelVisibilityEnum visibility;

    public CreateReplicateModelRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateReplicateModelRequestBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public CreateReplicateModelRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateReplicateModelRequestBuilder withVisibility(ReplicateModelVisibilityEnum visibility) {
        this.visibility = visibility;
        return this;
    }

    public CreateReplicateModelRequest build() {
        return new CreateReplicateModelRequest(name, owner, description, visibility);
    }
}
