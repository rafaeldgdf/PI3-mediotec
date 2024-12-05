package projeto.integrador3.senac.mediotec.pi3_mediotec.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.UsuarioCustomRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioCustomRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("LoginRequest recebido: " + loginRequest);

        // Verifica se o corpo da requisição veio corretamente
        if (loginRequest == null) {
            System.out.println("Corpo da requisição veio nulo!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida: Corpo não pode ser vazio.");
        }

        if (loginRequest.getIdentificador() == null || loginRequest.getSenha() == null || 
            loginRequest.getIdentificador().trim().isEmpty() || loginRequest.getSenha().trim().isEmpty()) {
            System.out.println("Identificador ou senha vazios!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Identificador e senha não podem ser vazios.");
        }

        String identificador = loginRequest.getIdentificador().trim();
        String senha = loginRequest.getSenha().trim();

        // Log detalhado para depuração
        System.out.println("Identificador recebido: " + identificador);
        System.out.println("Senha recebida: " + senha);

        // Consulta unificada para encontrar o usuário
        var result = usuarioRepository.findUserByEmailAndPassword(identificador, senha);

        if (result.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com as credenciais fornecidas.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }

        // Processa o resultado da consulta
        Object[] userData = result.get();
        String tipoUsuario = (String) userData[0]; // "coordenador", "professor" ou "aluno"
        Object usuario = userData[1];             // Objeto correspondente

        // Retorna a resposta
        System.out.println("Usuário encontrado: " + usuario);
        return ResponseEntity.ok(new LoginResponse(tipoUsuario, usuario));
    }
}
