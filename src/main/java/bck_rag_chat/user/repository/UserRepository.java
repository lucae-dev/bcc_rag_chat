package bck_rag_chat.user.repository;

import bck_rag_chat.base.mapper.BaseMapper;
import bck_rag_chat.base.repository.BaseRepository;
import bck_rag_chat.mapper.primary.UserMapper;
import bck_rag_chat.user.model.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserRepository extends BaseRepository<User> {

    private final UserMapper userMapper;
    private final SqlSessionFactory sqlSessionFactory;

    public UserRepository(UserMapper userMapper, SqlSessionFactory sqlSessionFactory) {
        this.userMapper = userMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    protected BaseMapper<User> getMapper() {
        return userMapper;
    }

    public User findByEmail(String email) {
        return userMapper.findByParams(null, email, null,null,null,null,null).stream()
                .findFirst()
                .orElse(null);
    }
}
