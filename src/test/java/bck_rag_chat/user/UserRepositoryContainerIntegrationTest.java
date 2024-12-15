package bck_rag_chat.user;

import bck_rag_chat.BaseContainerIntegrationTest;
import bck_rag_chat.user.model.User;
import bck_rag_chat.user.repository.UserRepository;
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
