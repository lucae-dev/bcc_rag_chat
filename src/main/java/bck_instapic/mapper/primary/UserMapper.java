package bck_instapic.mapper.primary;

import bck_instapic.base.mapper.BaseMapper;
import bck_instapic.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;
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
