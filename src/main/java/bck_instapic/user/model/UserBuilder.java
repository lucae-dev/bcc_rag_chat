package bck_instapic.user.model;

import java.time.Instant;
import java.util.UUID;

public final class UserBuilder {
    private String email;
    private String password;
    private String googleId;
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withGoogleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public UserBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserBuilder withInsertDate(Instant insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public UserBuilder withUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public UserBuilder withVersion(long version) {
        this.version = version;
        return this;
    }

    public UserBuilder withOrderUnique(long orderUnique) {
        this.orderUnique = orderUnique;
        return this;
    }

    public User build() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setGoogleId(googleId);
        user.setId(id);
        user.setInsertDate(insertDate);
        user.setUpdateDate(updateDate);
        user.setVersion(version);
        user.setOrderUnique(orderUnique);
        return user;
    }
}
