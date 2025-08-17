package com.foro.api.domain.topico;

import com.foro.api.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
    Page<Topico> findByUsuario(Usuario usuario, Pageable pageable);
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
}
