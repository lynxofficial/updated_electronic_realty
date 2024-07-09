package ru.realty.erealty.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.realty.erealty.repository.UserRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(new CustomUserDetailsService(userRepository));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    .csrf()
                    .disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/user/**")
                    .hasRole("USER")
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/deleteRealtyObjects")
                    .hasRole("ADMIN")
                    .requestMatchers("/deleteUsers")
                    .hasRole("ADMIN")
                    .requestMatchers("/**")
                    .permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/signIn")
                    .loginProcessingUrl("/userLogin")
                    .successHandler(customAuthSuccessHandler)
                    .permitAll();
            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
