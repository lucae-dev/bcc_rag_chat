package bck_instapic.user;

import bck_instapic.BaseContainerIntegrationTest;
import bck_instapic.user.model.User;
import bck_instapic.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryContainerIntegrationTest extends BaseContainerIntegrationTest {
    @Autowired private UserRepository userRepository;

    @Test
    void insert_shouldInsert() {
        // given
        User user = random(User.class);

        // when
        userRepository.insert(user);

        // then
        User loadedUser = userRepository.findById(user.getId());
        assertEquals(user, loadedUser);
    }
}
