package bck_instapic.authentication.controller.request;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public record LoginRequest (
        @NotNull String email,
        @NotNull String password
) implements AuthenticationRequest {
}
