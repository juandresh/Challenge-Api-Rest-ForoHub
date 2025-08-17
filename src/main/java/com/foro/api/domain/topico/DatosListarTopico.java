package com.foro.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record DatosListarTopico(
        @NotBlank Long idAutor,
        @NotBlank Long id,
        @NotBlank String titulo,
        @NotBlank String curso,
        @NotBlank String mensaje,
        @NotBlank LocalDateTime fechaCreacion
) {
    public DatosListarTopico(Topico topico) {
        this(
                topico.getUsuario().getId(),
                topico.getId(),
                topico.getTitulo(),
                topico.getCurso(),
                topico.getMensaje(),
                topico.getFechaCreacion()
        );
    }
}
