package com.zenvok.gestionusuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zenvok.gestionusuario.model.TipoUsuario;
import com.zenvok.gestionusuario.model.Usuario;
import com.zenvok.gestionusuario.repository.TipoUsuarioRepository;
import com.zenvok.gestionusuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {

        if (tipoUsuarioRepository.count() == 0) {
            log.info("Cargando tipos de usuario iniciales...");

            TipoUsuario admin = tipoUsuarioRepository.save(new TipoUsuario(null, "Administrador"));
            TipoUsuario bodeguero = tipoUsuarioRepository.save(new TipoUsuario(null, "Bodeguero"));
            TipoUsuario repartidor = tipoUsuarioRepository.save(new TipoUsuario(null, "Repartidor"));
            TipoUsuario cliente = tipoUsuarioRepository.save(new TipoUsuario(null, "Cliente"));

            log.info("Tipos de usuario cargados correctamente.");

            if (usuarioRepository.count() == 0) {
                log.info("Cargando usuarios iniciales...");

                Usuario u1 = new Usuario();
                u1.setRut("12345678-9");
                u1.setNombre("Adbeel");
                u1.setApellido("Rodriguez");
                u1.setCorreo("adbeel@test.com");
                u1.setContrasena("123456");
                u1.setTipoUsuario(admin);
                usuarioRepository.save(u1);

                Usuario u2 = new Usuario();
                u2.setRut("98765432-1");
                u2.setNombre("Camilo");
                u2.setApellido("Jimenez");
                u2.setCorreo("camilo@test.com");
                u2.setContrasena("123456");
                u2.setTipoUsuario(bodeguero);
                usuarioRepository.save(u2);

                Usuario u3 = new Usuario();
                u3.setRut("11111111-1");
                u3.setNombre("Javier");
                u3.setApellido("Dumfer");
                u3.setCorreo("javier@test.com");
                u3.setContrasena("123456");
                u3.setTipoUsuario(cliente);
                usuarioRepository.save(u3);

                log.info("Usuarios iniciales cargados correctamente.");
            }

            return;
        }

        if (usuarioRepository.count() == 0) {
            log.info("Tipos ya existentes. Cargando usuarios iniciales...");

            TipoUsuario admin = tipoUsuarioRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("No existe TipoUsuario con ID 1"));

            TipoUsuario bodeguero = tipoUsuarioRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("No existe TipoUsuario con ID 2"));

            TipoUsuario cliente = tipoUsuarioRepository.findById(4L)
                    .orElseThrow(() -> new RuntimeException("No existe TipoUsuario con ID 4"));

            Usuario u1 = new Usuario();
            u1.setRut("12345678-9");
            u1.setNombre("Adbeel");
            u1.setApellido("Rodriguez");
            u1.setCorreo("adbeel@test.com");
            u1.setContrasena("123456");
            u1.setTipoUsuario(admin);
            usuarioRepository.save(u1);

            Usuario u2 = new Usuario();
            u2.setRut("98765432-1");
            u2.setNombre("Camilo");
            u2.setApellido("Jimenez");
            u2.setCorreo("camilo@test.com");
            u2.setContrasena("123456");
            u2.setTipoUsuario(bodeguero);
            usuarioRepository.save(u2);

            Usuario u3 = new Usuario();
            u3.setRut("11111111-1");
            u3.setNombre("Javier");
            u3.setApellido("Dumfer");
            u3.setCorreo("javier@test.com");
            u3.setContrasena("123456");
            u3.setTipoUsuario(cliente);
            usuarioRepository.save(u3);

            log.info("Usuarios iniciales cargados correctamente.");
        } else {
            log.info("Usuarios y tipos de usuario ya existen. Se omite inicialización.");
        }
    }
}