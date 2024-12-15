package bck_rag_chat.mapper.primary;

import bck_rag_chat.base.mapper.BaseMapper;
import bck_rag_chat.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByEmail(String email);

    List<User> findByParams(@Param("id") UUID id,
                            @Param("email") String email,
                            @Param("googleId") String googleId,
                            @Param("insertDate") Instant insertDate,
                            @Param("updateDate") Instant updateDate,
                            @Param("version") Long version,
                            @Param("orderUnique") Long orderUnique);
}
