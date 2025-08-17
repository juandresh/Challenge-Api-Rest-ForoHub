package com.foro.api.domain.topico;

import com.foro.api.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String curso,
        @NotBlank
        String mensaje) {
}
