package com.example.projectlogin;

import com.example.projectlogin.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectloginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectloginApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioService usuarioService) {
		return args -> {
			try {
				// Creamos un usuario "admin" con contraseña "123456" de manera inicial
				usuarioService.registrarUsuario("admin", "123456");
				System.out.println(">>> [OK] Usuario de prueba 'admin' inyectado correctamente.");
			} catch (Exception e) {
				// Evita que lance errores si el usuario ya existía al reiniciar el servidor
				System.out.println(">>> [INFO] El usuario de prueba ya existe.");
			}
		};
	}
}