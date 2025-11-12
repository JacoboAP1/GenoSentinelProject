package com.genosentinel.authentication.controller;

import com.genosentinel.authentication.exceptions.MissingFillFieldsException;
import com.genosentinel.authentication.exceptions.UserNotFoundException;
import com.genosentinel.authentication.exceptions.UsernameAlreadyExistsException;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import io.swagger.v3.oas.annotations.Operation; // Importa las librerías para documentar endpoints con etiquetas de swagger
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para la autenticación y registro de usuarios.
 * Sistema con un único rol ("USER") según requerimientos del proyecto GenoSentinel.
 */
@Tag(name = "Autenticación", description = "Endpoints para el registro y login de usuarios") // Swagger annotation
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
    @Operation (
            summary = "Iniciar sesión",
            description = "Autentica al usuario con su nombre de usuario, correo y contraseña. Retorna un token JWT si las credenciales son válidas"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, retorna token JWT"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "400", description = "Solicitud mal formada"),
            @ApiResponse(responseCode = "404", description = "No se encontró el nombre de usuario")
    }) //Swagger annotation
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String email = req.get("email");
        String password = req.get("password");

        if (username == null || username.isBlank()
                || email == null || email.isBlank()
                || password == null || password.isBlank()) {

            throw new MissingFillFieldsException("Fill all the fields");
        }

        // Busca usuario en base de datos
        var user = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("El nombre de usuario no existe"));

        // Valida que el email coincida
        if (!email.equals(user.getEmail())) {
            throw new InvalidEmailException("El correo electrónico no coincide con el registrado.");
        }

        // Si las credenciales son incorrectas, lanza AuthenticationException → 401 por defecto
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

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
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario con su nombre, correo y contraseña. Retorna un token JWT al registrarse correctamente"
    ) //Swagger annotation
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
            @ApiResponse(responseCode = "409", description = "El nombre de usuario ya existe")
    }) //Swagger annotation
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        if (req.getUsername() == null || req.getUsername().isBlank()
        || req.getEmail() == null || req.getEmail().isBlank()
        ||  req.getPassword() == null || req.getPassword().isBlank()) {

            throw new MissingFillFieldsException("Fill all the fields");
        }
        if (usuarioRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Enter another username");
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
