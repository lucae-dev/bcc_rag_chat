package bck_instapic.authentication.controller.request;

public final class LoginRequestBuilder {
    private String email;
    private String password;

    public LoginRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public LoginRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginRequest build() {
        return new LoginRequest(email, password);
    }
}
