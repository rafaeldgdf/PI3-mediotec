package projeto.integrador3.senac.mediotec.pi3_mediotec.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Método para gerar o hash da senha
    public static String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    // Método para verificar se a senha fornecida corresponde ao hash armazenado
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}