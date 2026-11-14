package com.sigau.config;

import com.sigau.usuario.Roles;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login")
                        .permitAll()
                        .requestMatchers("/api/usuarios", "/api/usuarios/**")
                        .hasRole(Roles.NOMBRE_ADMINISTRATIVO)
                        .requestMatchers(HttpMethod.POST,
                                "/api/inspecciones",
                                "/api/evidencias-inspeccion",
                                "/api/intervenciones",
                                "/api/verificaciones",
                                "/api/certificaciones")
                        .hasRole(Roles.NOMBRE_INSPECTOR)
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/intervenciones/**")
                        .hasRole(Roles.NOMBRE_INSPECTOR)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
                        .authenticationEntryPoint(
                                (request, response, exception) ->
                                        responder401(response)
                        )
                        .accessDeniedHandler(
                                (request, response, exception) ->
                                        responder403(response)
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            Number rolId = jwt.getClaim("rolId");

            if (rolId == null) {
                return List.of();
            }

            String autoridad = switch (rolId.intValue()) {
                case Roles.INSPECTOR -> "ROLE_" + Roles.NOMBRE_INSPECTOR;
                case Roles.ADMINISTRATIVO -> "ROLE_" + Roles.NOMBRE_ADMINISTRATIVO;
                default -> null;
            };

            if (autoridad == null) {
                return List.of();
            }

            return List.of(new SimpleGrantedAuthority(autoridad));
        });

        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${sigau.cors.origenes:http://localhost:5173}")
            List<String> origenes) {

        CorsConfiguration configuracion = new CorsConfiguration();

        configuracion.setAllowedOrigins(origenes);
        configuracion.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuracion.setAllowedHeaders(
                List.of("Authorization", "Content-Type"));
        configuracion.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource fuente =
                new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/api/**", configuracion);

        return fuente;
    }

    private void responder401(HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write("""
            {
                "error": "No autenticado",
                "mensaje": "Se requiere un token válido para acceder al recurso."
            }
            """);
    }

    private void responder403(HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write("""
            {
                "error": "Acceso denegado",
                "mensaje": "No tiene permisos para realizar esta operación."
            }
            """);
    }
}
