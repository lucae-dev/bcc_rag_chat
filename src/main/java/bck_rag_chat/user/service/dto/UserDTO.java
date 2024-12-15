package bck_rag_chat.user.service.dto;

import java.time.Instant;
import java.util.UUID;

public record UserDTO (
        String email,
        String password,
        String googleId,
        UUID id,
        Instant insertDate,
        Instant updateDate,
        long version,
        long orderUnique
) {

}
