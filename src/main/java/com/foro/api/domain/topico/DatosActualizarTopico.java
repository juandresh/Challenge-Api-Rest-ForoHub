package com.foro.api.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        String titulo,
        String curso,
        String mensaje
        ) {
}
