package com.sigau.config;

import com.sigau.usuario.Usuario;
import com.sigau.usuario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner inicializarUsuarioAdministrativo(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            String email = "admin@sigau.com";

            if (usuarioRepository.findByEmail(email).isEmpty()) {

                String password = System.getenv("SIGAU_ADMIN_PASSWORD");

                if (password == null || password.isBlank()) {
                    throw new IllegalStateException(
                            "Debe configurar la variable SIGAU_ADMIN_PASSWORD "
                                    + "para crear el usuario administrativo."
                    );
                }

                Usuario usuario = new Usuario();

                usuario.setNombre("Administrador");
                usuario.setApellido("SIGAU");
                usuario.setEmail(email);
                usuario.setPasswordHash(
                        passwordEncoder.encode(password)
                );
                usuario.setRolId(2);
                usuario.setActivo(true);

                usuarioRepository.save(usuario);
            }
        };
    }
}
