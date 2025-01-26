package bck_instapic.authentication.controller.request;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import java.util.Date;

public record RegistrationRequest(
        @NotNull String email,
        @NotNull String password,
        Date birthday
) implements AuthenticationRequest {

}
