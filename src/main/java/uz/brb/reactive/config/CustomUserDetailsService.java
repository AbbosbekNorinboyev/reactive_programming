package uz.brb.reactive.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uz.brb.reactive.repository.AuthUserRepository;

@Service // 🔴 Bu juda muhim!
@RequiredArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final AuthUserRepository authUserRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().name()) // ⚠️ ENUM -> String
                        .build());
    }
}
