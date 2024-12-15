package bck_rag_chat.authentication.controller.response;

public final class RegistrationResponseBuilder {
    private String jwtToken;

    public RegistrationResponseBuilder withJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    public RegistrationResponse build() {
        return new RegistrationResponse(jwtToken);
    }
}
