package br.com.joaojuniodev.corefitpro.security.password;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import java.util.Map;

public final class PasswordEncoding {
    private PasswordEncoding() {}

    public static PasswordEncoder createEncoder() {
        PasswordEncoder pbkdf2 = new Pbkdf2PasswordEncoder("", 8, 1815000,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder("pbkdf2", Map.of("pbkdf2", pbkdf2));
        encoder.setDefaultPasswordEncoderForMatches(pbkdf2);
        return encoder;
    }

    public static boolean isEncoded(String password) {
        // O formato sem prefixo é mantido apenas para hashes PBKDF2 legados.
        return password != null && (password.startsWith("{pbkdf2}") || password.matches("(?i)^[a-f0-9]{80}$"));
    }
}
