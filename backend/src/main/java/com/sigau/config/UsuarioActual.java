package com.sigau.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UsuarioActual {

    public Integer id() {
        Authentication autenticacion =
                SecurityContextHolder.getContext().getAuthentication();

        if (autenticacion instanceof JwtAuthenticationToken token) {
            Object valor = token.getToken().getClaim("userId");

            if (valor instanceof Number numero) {
                return numero.intValue();
            }
        }

        throw new AuthenticationCredentialsNotFoundException(
                "No hay un usuario autenticado");
    }
}
