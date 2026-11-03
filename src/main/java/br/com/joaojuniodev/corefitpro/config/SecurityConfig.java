package br.com.joaojuniodev.corefitpro.config;

import br.com.joaojuniodev.corefitpro.security.jwt.JwtTokenFilter;
import br.com.joaojuniodev.corefitpro.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public SecurityConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter filter = new JwtTokenFilter(tokenProvider);
        return http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(
                    "/auth/sign",
                    "/auth/refresh/**",
                    "/auth/register",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/ws/**"
                ).permitAll()
                .requestMatchers("/api/student/**").hasAuthority("TRAINEE")
                .requestMatchers("/api/account/**").authenticated()
                .requestMatchers("/api/**").hasAnyAuthority("PERSONAL", "ADMIN")
                .requestMatchers("/users").denyAll()
                .anyRequest().denyAll()
            )
            .cors(cors -> {})
            .build();
    }
}
