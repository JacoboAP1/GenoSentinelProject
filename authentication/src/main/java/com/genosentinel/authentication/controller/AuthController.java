package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.service.JwtService;
import com.genosentinel.authentication.models.entities.Role;
import com.genosentinel.authentication.models.entities.Users;
import com.genosentinel.authentication.models.dto.RegisterRequest;
import com.genosentinel.authentication.repository.RoleRepository;
import com.genosentinel.authentication.repository.UsersRepository;
import com.genosentinel.authentication.exceptions.InvalidEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controlador para la autenticación y registro de usuarios.
 * Sistema con un único rol ("USER") según requerimientos del proyecto GenoSentinel.
 */
@RestController
@RequestMapping("") // No agregar prefijo; ya está en server.servlet.context-path
@RequiredArgsConstructor
public class AuthController {

    /**
     * AuthenticationManager de Spring Security para autenticar usuarios.
     */
    private final AuthenticationManager authManager;
    /**
     * Repositorio para la gestión de usuarios.
     */
    private final UsersRepository usuarioRepo;
    /**
     * Repositorio para la gestión de roles.
     */
    private final RoleRepository rolRepo;
    /**
     * Servicio para la gestión de JWT.
     */
    private final JwtService jwt;
    /**
     * PasswordEncoder para encriptar contraseñas.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Endpoint de login de usuario.
     * Valida credenciales, correo y genera un token JWT.
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String email = req.get("email");
        String password = req.get("password");

        // Autentica usuario con Spring Security
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Busca usuario en base de datos
        var user = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Valida que el email coincida
        if (email == null || !email.equals(user.getEmail())) {
            throw new InvalidEmailException("El correo electrónico no coincide con el registrado.");
        }

        // Genera token con rol único "USER"
        List<String> roles = List.of("USER");
        String token = jwt.generate(user.getUsername(), roles);

        return Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "roles", roles
        );
    }

    /**
     * Endpoint para registrar nuevos usuarios.
     * Crea usuario con rol único "USER" y devuelve token JWT.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing username or password");
        }
        if (usuarioRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        // Obtiene o crea el rol único "USER"
        Role userRole = rolRepo.findByName("USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("USER");
            return rolRepo.save(newRole);
        });

        Users user = new Users();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(Set.of(userRole));
        usuarioRepo.save(user);

        String token = jwt.generate(user.getUsername(), List.of("USER"));

        return Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "roles", List.of("USER")
        );
    }

    /**
     * Maneja errores de autenticación devolviendo un mensaje estándar.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public Map<String, String> onAuthError(Exception e) {
        return Map.of("error", "Bad credentials");
    }
}
