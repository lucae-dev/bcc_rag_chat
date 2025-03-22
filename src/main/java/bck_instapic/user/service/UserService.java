package bck_instapic.user.service;

import bck_instapic.user.model.User;
import bck_instapic.user.model.UserBuilder;
import bck_instapic.user.repository.UserRepository;
import bck_instapic.user.service.dto.UserDTO;
import bck_instapic.user.service.dto.UserDTOBuilder;
import bck_instapic.user.service.exception.UserCreationException;
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
        return new UserBuilder()
                .withEmail(userDTO.email())
                .withPassword(userDTO.password())
                .withGoogleId(userDTO.googleId())
                .build();
    }
}
