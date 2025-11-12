package utex.edu.mx.server.modules.users.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utex.edu.mx.server.config.ApiResponse;
import utex.edu.mx.server.modules.users.model.IUsuarioRepository;
import utex.edu.mx.server.modules.users.model.Usuario;
import utex.edu.mx.server.messages.ResponseMessages;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final IUsuarioRepository repository;

    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }



    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Usuario> save(Usuario usuario) {
        try {
            Usuario nuevoUsuario = repository.save(usuario);
            return ApiResponse.created(nuevoUsuario, ResponseMessages.USUARIO_CREADO_EXITOSO.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ResponseMessages.ERROR_GUARDAR.format(e.getMessage())
            );
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Usuario>> findAll() {
        List<Usuario> usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            return ApiResponse.success(usuarios, ResponseMessages.NO_USUARIOS_ENCONTRADOS.getMessage());
        }
        return ApiResponse.success(usuarios, ResponseMessages.USUARIOS_OBTENIDOS_EXITOSO.getMessage());
    }

    @Transactional(readOnly = true)
    public ApiResponse<Usuario> findById(Long id) {
        Optional<Usuario> usuarioOpt = repository.findById(id);

        if (usuarioOpt.isEmpty()) {

            return ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(),
                    ResponseMessages.USUARIO_NO_ENCONTRADO_ID.format(id)
            );
        }

        return ApiResponse.success(usuarioOpt.get(), ResponseMessages.USUARIO_OBTENIDO_EXITOSO.getMessage());
    }


    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Usuario> update(Long id, Usuario usuarioActualizado) {
        if (!repository.existsById(id)) {

            return ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(),
                    ResponseMessages.USUARIO_NO_ENCONTRADO_UPDATE.format(id)
            );
        }


        usuarioActualizado.setId(id);

        try {
            Usuario usuarioEditado = repository.save(usuarioActualizado);
            return ApiResponse.success(usuarioEditado, ResponseMessages.USUARIO_ACTUALIZADO_EXITOSO.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ResponseMessages.ERROR_ACTUALIZAR.format(e.getMessage())
            );
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Void> delete(Long id) {
        if (!repository.existsById(id)) {
            return ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(),
                    ResponseMessages.USUARIO_NO_ENCONTRADO_DELETE.format(id)
            );
        }

        try {
            repository.deleteById(id);
            return ApiResponse.success(null, ResponseMessages.USUARIO_ELIMINADO_EXITOSO.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ResponseMessages.ERROR_ELIMINAR.format(e.getMessage())
            );
        }
    }
}