package bck_rag_chat.authentication.controller.request;

import java.util.Date;

public final class RegistrationRequestBuilder {
    private String email;
    private String password;
    private Date birthday;

    public RegistrationRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public RegistrationRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public RegistrationRequestBuilder withBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public RegistrationRequest build() {
        return new RegistrationRequest(email, password, birthday);
    }
}
