package bck_instapic.authentication.controller.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RegistrationResponse(
        String jwtToken
) {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
