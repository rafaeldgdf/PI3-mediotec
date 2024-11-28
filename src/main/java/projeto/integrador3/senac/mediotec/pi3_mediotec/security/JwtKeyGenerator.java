package projeto.integrador3.senac.mediotec.pi3_mediotec.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtKeyGenerator {

    public static void main(String[] args) {
        // Gera uma chave secreta segura com o tamanho adequado para HS512
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);  // Chave com tamanho adequado para HS512

        // Converte a chave gerada para Base64 para usá-la como jwt.secret
        String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());

        // Exibe a chave gerada
        System.out.println("Chave JWT secreta: " + base64Key);
    }
}

