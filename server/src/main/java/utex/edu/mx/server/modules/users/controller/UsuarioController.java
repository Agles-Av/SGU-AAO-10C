package utex.edu.mx.server.modules.users.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utex.edu.mx.server.config.ApiResponse;
import utex.edu.mx.server.modules.users.controller.dto.UsuarioRequestDTO;
import utex.edu.mx.server.modules.users.controller.dto.UsuarioResponseDTO;
import utex.edu.mx.server.modules.users.model.Usuario;
import utex.edu.mx.server.modules.users.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private Usuario mapToEntity(UsuarioRequestDTO dto) {
        return new Usuario(dto.nombre(), dto.apellidos(), dto.email());
    }

    private UsuarioResponseDTO mapToDto(Usuario entity) {
        return new UsuarioResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidos(),
                entity.getEmail()
        );
    }


    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> save(@Valid @RequestBody UsuarioRequestDTO dto) {

        Usuario usuarioParaGuardar = mapToEntity(dto);
        ApiResponse<Usuario> serviceResponse = usuarioService.save(usuarioParaGuardar);

        UsuarioResponseDTO responseDto = null;
        if (serviceResponse.getData() != null) {
            responseDto = mapToDto(serviceResponse.getData());
        }

        ApiResponse<UsuarioResponseDTO> finalResponse = new ApiResponse<>(
                serviceResponse.getStatus(),
                responseDto,
                serviceResponse.getMessage()
        );

        return new ResponseEntity<>(finalResponse, HttpStatus.valueOf(finalResponse.getStatus()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponseDTO>>> findAll() {

        ApiResponse<List<Usuario>> serviceResponse = usuarioService.findAll();

        List<UsuarioResponseDTO> dtoList = serviceResponse.getData().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ApiResponse<List<UsuarioResponseDTO>> finalResponse = new ApiResponse<>(
                serviceResponse.getStatus(),
                dtoList,
                serviceResponse.getMessage()
        );

        return new ResponseEntity<>(finalResponse, HttpStatus.valueOf(finalResponse.getStatus()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        Usuario usuarioParaActualizar = mapToEntity(dto);

        ApiResponse<Usuario> serviceResponse = usuarioService.update(id, usuarioParaActualizar);

        UsuarioResponseDTO responseDto = null;
        if (serviceResponse.getData() != null) {
            responseDto = mapToDto(serviceResponse.getData());
        }

        ApiResponse<UsuarioResponseDTO> finalResponse = new ApiResponse<>(
                serviceResponse.getStatus(),
                responseDto,
                serviceResponse.getMessage()
        );

        return new ResponseEntity<>(finalResponse, HttpStatus.valueOf(finalResponse.getStatus()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        ApiResponse<Void> serviceResponse = usuarioService.delete(id);

        ApiResponse<Void> finalResponse = new ApiResponse<>(
                serviceResponse.getStatus(),
                null,
                serviceResponse.getMessage()
        );

        return new ResponseEntity<>(finalResponse, HttpStatus.valueOf(finalResponse.getStatus()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> findById(@PathVariable Long id) {

        ApiResponse<Usuario> serviceResponse = usuarioService.findById(id);

        UsuarioResponseDTO responseDto = null;
        if (serviceResponse.getData() != null) {
            responseDto = mapToDto(serviceResponse.getData());
        }

        ApiResponse<UsuarioResponseDTO> finalResponse = new ApiResponse<>(
                serviceResponse.getStatus(),
                responseDto,
                serviceResponse.getMessage()
        );

        return new ResponseEntity<>(finalResponse, HttpStatus.valueOf(finalResponse.getStatus()));
    }
}
