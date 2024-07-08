package ru.realty.erealty.config;

import lombok.RequiredArgsConstructor;
import ru.realty.erealty.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByEmail(username)
                .map(CustomUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
