package utex.edu.mx.server.modules.users.controller.dto;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String apellidos,
        String email
) {}