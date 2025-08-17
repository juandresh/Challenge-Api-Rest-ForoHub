package com.foro.api.domain.topico;

import com.foro.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="topico",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo", "mensaje"})})
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Topico {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String titulo;

    private String mensaje;

    private LocalDateTime fecha;

    private boolean status;

    private String curso;

    public LocalDateTime getFechaCreacion() {
        return fecha;
    }

    public Topico(@Valid DatosRegistroTopico datos, @Valid Usuario usuario) {
        this.usuario = usuario;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.curso = datos.curso();
        this.fecha = LocalDateTime.now();
        this.status = true;
    }

    public void desactivar() {
        this.status = false;
    }

    public void actualizar(DatosActualizarTopico datos) {
        if (datos.curso() != null){
            this.curso = datos.curso();
        }
        if (datos.titulo() != null){
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
    }
}
