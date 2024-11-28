package projeto.integrador3.senac.mediotec.pi3_mediotec.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;  // CORRIGIDO: Importação correta do @Value
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.security.core.Authentication;

@Component  // Anotação para registrar o filtro como um Bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")  // CORRIGIDO: Usando @Value para injetar a chave jwt.secret do application.properties
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove o prefixo "Bearer "

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))  // Usando a chave jwtSecret
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                List<SimpleGrantedAuthority> authorities = extractAuthorities(claims);

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Log para verificar token JWT
                System.out.println("Token JWT autenticado com sucesso para: " + username);

            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido ou expirado");
            }
        }

        chain.doFilter(request, response);
    }

    // Método para extrair as autoridades (roles) do token JWT
    private List<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        // Pega o claim "role"
        Object roleClaim = claims.get("role");

        // Se o role for uma lista de strings
        if (roleClaim instanceof List<?>) {
            try {
                // Fazemos a verificação explícita de que é uma lista de strings
                @SuppressWarnings("unchecked")  // Suprimir o warning de cast não verificado
                List<String> roles = (List<String>) roleClaim;
                return roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))  // Adiciona o prefixo "ROLE_"
                            .collect(Collectors.toList());
            } catch (ClassCastException e) {
                // Caso o cast falhe, retornamos uma lista vazia
                return List.of();
            }
        }

        // Se o role for uma única string
        if (roleClaim instanceof String) {
            String role = (String) roleClaim;
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));  // Retorna uma lista com uma única autoridade
        }

        // Se não houver role ou se o tipo for inesperado, retornamos uma lista vazia
        return List.of();
    }
}
