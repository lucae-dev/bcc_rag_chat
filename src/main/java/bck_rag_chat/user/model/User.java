package bck_rag_chat.user.model;

import bck_rag_chat.base.entity.BaseEntity;
import lombok.*;

@Data
@ToString(callSuper = true, includeFieldNames = true, doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with", builderClassName = "UserBuilder")
public class User extends BaseEntity {
    private String email;
    private String password;
    private String googleId;
}
