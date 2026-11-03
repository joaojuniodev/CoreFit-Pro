package br.com.joaojuniodev.corefitpro.config;

import br.com.joaojuniodev.corefitpro.security.password.PasswordEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoding.createEncoder();
    }
}
