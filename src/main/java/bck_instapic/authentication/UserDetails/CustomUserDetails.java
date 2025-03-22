package bck_instapic.authentication.UserDetails;

import bck_instapic.user.service.dto.UserDTO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final UserDTO user;

    public CustomUserDetails(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.email();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert your user’s roles/permissions to GrantedAuthority
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
