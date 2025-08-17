package com.foro.api.infra.security;

import com.foro.api.domain.usuario.Usuario;
import com.foro.api.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //System.out.println("--- INICIANDO SECURITY FILTER ---"); // LOG 1: ¿Se ejecuta el filtro?
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
        //    System.out.println("Token JWT recibido: " + tokenJWT); // LOG 2: ¿Recibimos el token?
            try {
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = repository.findByEmail(subject)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

          //      System.out.println("Usuario autenticado: " + subject); // LOG 3: ¿Se autenticó el usuario?
          //      System.out.println("Roles del usuario: " + usuario.getAuthorities()); // LOG 4: ¿Qué roles tiene?

            } catch (Exception e) {
          //      System.err.println("Error al procesar el token: " + e.getMessage());
            }
        } else {
           // System.out.println("No se encontró token JWT en la cabecera."); // LOG 5: No hay token
        }

        filterChain.doFilter(request, response);
        // System.out.println("--- FINALIZANDO SECURITY FILTER ---");
    }

    private String recuperarToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
