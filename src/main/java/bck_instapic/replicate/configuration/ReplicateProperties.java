package bck_instapic.replicate.configuration;

public class ReplicateProperties {

    private String baseUrl;
    private String apiKey;

    public ReplicateProperties(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }
}
