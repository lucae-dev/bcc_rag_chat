package bck_instapic.authentication.controller.request;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record LoginRequest (
        @NotNull String email,
        @NotNull String password
) implements AuthenticationRequest {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
