package bck_instapic.replicate.configuration;

public final class ReplicatePropertiesBuilder {
    private String baseUrl;
    private String apiKey;

    public ReplicatePropertiesBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ReplicatePropertiesBuilder withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public ReplicateProperties build() {
        return new ReplicateProperties(baseUrl, apiKey);
    }
}
