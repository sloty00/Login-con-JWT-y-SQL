package com.example.projectlogin.service;

import com.example.projectlogin.model.Usuario;
import com.example.projectlogin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyección de dependencias limpia

    public boolean autenticar(String username, String password) {
        // 1. Buscamos al usuario por su username en la base de datos
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        // 2. Si el usuario existe, comparamos los textos de las contraseñas
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getPassword().equals(password); // Devuelve true si coinciden
        }
        
        return false; // Retorna false si no existe o la clave está incorrecta
    }

    // Método auxiliar para insertar usuarios en las pruebas
    public Usuario registrarUsuario(String username, String password) {
        Usuario nuevo = new Usuario(username, password);
        return usuarioRepository.save(nuevo);
    }
}