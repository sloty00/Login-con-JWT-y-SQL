package com.example.projectlogin.controller;

import com.example.projectlogin.dto.LoginRequest;
import com.example.projectlogin.dto.LoginResponse; // Importación para el JSON de respuesta con el Token
import com.example.projectlogin.service.UsuarioService;
import com.example.projectlogin.service.JwtService;       // Importación de tu validador JWT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite conectar con entornos de desarrollo como React sin bloqueos de CORS
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService; // Inyección del servicio JWT mapeada correctamente

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        
        // Comprobamos las credenciales contra la base de datos MySQL
        boolean loginExitoso = usuarioService.autenticar(request.getUsername(), request.getPassword());

        if (loginExitoso) {
            // 1. Si las credenciales son correctas, fabricamos el token firmado con Auth0
            String tokenGenerado = jwtService.crearToken(request.getUsername());
            
            // 2. Retorna un estado HTTP 200 OK junto con el DTO encapsulando el JWT
            return ResponseEntity.ok(new LoginResponse(tokenGenerado, "¡Login exitoso! Acceso concedido."));
        } else {
            // Retorna un estado HTTP 401 Unauthorized si la clave o usuario fallan
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas. Acceso denegado.");
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> obtenerDashboard(@RequestHeader(value = "Authorization", required = false) String bearerToken) {
        
        // 1. Validar que la cabecera no venga vacía y que empiece con la palabra "Bearer "
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Acceso denegado: No se proporcionó un token válido.");
        }

        // 2. Extraer el string puro del token (quitándole los primeros 7 caracteres de "Bearer ")
        String tokenPuro = bearerToken.substring(7);

        // 3. Usar el servicio para verificar la firma y el tiempo de expiración
        String username = jwtService.validarTokenYObtenerUsuario(tokenPuro);

        if (username != null) {
            // Si el token es legítimo, devolvemos los datos VIP
            return ResponseEntity.ok("¡Bienvenido al Dashboard VIP, " + username + "! Este mensaje viene directo del servidor protegido por tu JWT.");
        } else {
            // Si el token fue alterado, expiró o está mal estructurado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Acceso denegado: El token expiró o es inválido.");
        }
    }
}