package com.foro.api.controller;

import com.foro.api.domain.topico.*;
import com.foro.api.domain.usuario.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody DatosRegistroTopico datosTopico,
                                       @AuthenticationPrincipal Usuario usuarioAutenticado,
                                       UriComponentsBuilder uriBuilder) {

        if (topicoRepository.findByTituloAndMensaje(datosTopico.titulo(), datosTopico.mensaje()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un topico con el mismo titulo y mensaje.");
        }

        Topico topico = new Topico(datosTopico, usuarioAutenticado);
        topicoRepository.save(topico);
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarTopico>> listadoTopicos(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListarTopico> pagina = topicoRepository.findAll(pageable).map(DatosListarTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListarTopico> detalleTopico(@PathVariable Long id) {

        var optionalTopico= topicoRepository.findById(id);

        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var datosTopico = new DatosListarTopico(optionalTopico.get());
        return ResponseEntity.ok(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosListarTopico> editarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datosTopico) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizar(datosTopico);
        return ResponseEntity.ok(new DatosListarTopico(topico));
    }
}
