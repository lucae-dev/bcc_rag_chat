package bck_rag_chat.user.service;

import bck_rag_chat.user.model.User;
import bck_rag_chat.user.repository.UserRepository;
import bck_rag_chat.user.service.dto.UserDTO;
import bck_rag_chat.user.service.dto.UserDTOBuilder;
import bck_rag_chat.user.service.exception.UserCreationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO create(UserDTO userDTO) throws UserCreationException {
        return Optional.of(userDTO)
                .map(this::convertUserDTOtoEntity)
                .map(userRepository::insert)
                .map(this::convertUserEntityToDto)
                .orElseThrow(UserCreationException::new);
    }

    public UserDTO loadByEmail(String email) {
        return Optional.ofNullable(email)
                .map(userRepository::findByEmail)
                .map(this::convertUserEntityToDto)
                .orElse(null);
    }

    private UserDTO convertUserEntityToDto(User entity) {
        return new UserDTOBuilder()
                .withId(entity.getId())
                .withInsertDate(entity.getInsertDate())
                .withVersion(entity.getVersion())
                .withUpdateDate(entity.getUpdateDate())
                .withOrderUnique(entity.getOrderUnique())
                .withGoogleId(entity.getGoogleId())
                .withEmail(entity.getEmail())
                .withPassword(entity.getPassword())
                .build();
    }

    private User convertUserDTOtoEntity(UserDTO userDTO) {
        return User.builder()
                .withEmail(userDTO.email())
                .withPassword(userDTO.password())
                .withGoogleId(userDTO.googleId())
                .build();
    }
}
