package bck_rag_chat.user.service.dto;

import java.time.Instant;
import java.util.UUID;

public final class UserDTOBuilder {
    private String email;
    private String password;
    private String googleId;
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;

    public UserDTOBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDTOBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDTOBuilder withGoogleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public UserDTOBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserDTOBuilder withInsertDate(Instant insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public UserDTOBuilder withUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public UserDTOBuilder withVersion(long version) {
        this.version = version;
        return this;
    }

    public UserDTOBuilder withOrderUnique(long orderUnique) {
        this.orderUnique = orderUnique;
        return this;
    }

    public UserDTO build() {
        return new UserDTO(email, password, googleId, id, insertDate, updateDate, version, orderUnique);
    }
}
