package utex.edu.mx.server.messages; // Nuevo paquete sugerido

public enum ResponseMessages {

    // ÉXITO
    USUARIO_CREADO_EXITOSO("Usuario creado exitosamente."),
    USUARIO_OBTENIDO_EXITOSO("Usuario obtenido exitosamente."),
    USUARIOS_OBTENIDOS_EXITOSO("Usuarios obtenidos exitosamente."),
    USUARIO_ACTUALIZADO_EXITOSO("Usuario actualizado correctamente."),
    USUARIO_ELIMINADO_EXITOSO("Usuario eliminado correctamente."),

    // ADVERTENCIAS / NO ENCONTRADO
    NO_USUARIOS_ENCONTRADOS("No se encontraron usuarios."),

    // ERRORES (Se usarán con format string)
    USUARIO_NO_ENCONTRADO_ID("Usuario con ID %d no encontrado."),
    USUARIO_NO_ENCONTRADO_UPDATE("No se puede actualizar. Usuario con ID %d no encontrado."),
    USUARIO_NO_ENCONTRADO_DELETE("No se pudo eliminar. Usuario con ID %d no encontrado."),
    ERROR_GUARDAR("Error al guardar el usuario: %s"),
    ERROR_ACTUALIZAR("Error al actualizar el usuario: %s"),
    ERROR_ELIMINAR("Error al eliminar el usuario: %s");


    private final String message;

    ResponseMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Método de utilidad para formatear mensajes que contienen placeholders.
     * @param args Argumentos a insertar en el mensaje.
     * @return El mensaje formateado.
     */
    public String format(Object... args) {
        return String.format(this.message, args);
    }
}