package projeto.integrador3.senac.mediotec.pi3_mediotec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Tentando autenticar: " + loginRequest.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Recupera a role diretamente do token de segurança
            String role = authentication.getAuthorities().stream()
                    .filter(auth -> auth.getAuthority().startsWith("ROLE_"))
                    .map(auth -> auth.getAuthority().substring(5))  // Retira "ROLE_" para retornar apenas a role
                    .findFirst()
                    .orElse("ROLE_DEFAULT");

            // Gerar o JWT com a role do usuário
            String jwt = jwtUtil.generateJwtToken(authentication.getName(), role);

            System.out.println("Autenticação bem-sucedida para: " + loginRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (Exception e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return ResponseEntity.status(403).body("Erro de autenticação");
        }
    }


}
