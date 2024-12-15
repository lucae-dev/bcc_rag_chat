package bck_rag_chat.configuration.system;

public final class SystemPropertiesBuilder {
    private String awsRegion;
    private String environment;

    public SystemPropertiesBuilder withAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
        return this;
    }

    public SystemPropertiesBuilder withEnvironment(String environment) {
        this.environment = environment;
        return this;
    }

    public SystemProperties build() {
        return new SystemProperties(awsRegion, environment);
    }
}
