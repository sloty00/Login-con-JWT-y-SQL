package com.example.projectlogin.repository;

import com.example.projectlogin.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring deduce la Query automáticamente: SELECT * FROM usuarios WHERE username = ?
    Optional<Usuario> findByUsername(String username);
}