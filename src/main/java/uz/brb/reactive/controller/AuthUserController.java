package uz.brb.reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uz.brb.reactive.config.CustomUserDetailsService;
import uz.brb.reactive.dto.UserDto;
import uz.brb.reactive.entity.AuthUser;
import uz.brb.reactive.enums.Roles;
import uz.brb.reactive.exception.ResourceNotFoundException;
import uz.brb.reactive.repository.AuthUserRepository;
import uz.brb.reactive.util.JwtUtil;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthUserController {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public Mono<?> register(@RequestBody UserDto request) {
        return authUserRepository.findByUsername(request.getUsername())
                .flatMap(authUser -> Mono.error(() -> new RuntimeException("NOT FOUND")))
                .switchIfEmpty(Mono.defer(() -> {
                    AuthUser user = new AuthUser();
                    user.setUsername(request.getUsername());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setRoles(Roles.USER);
                    return authUserRepository.save(user).thenReturn("REGISTER SUCCESSFULLY");
                }));
    }

    @PostMapping("/login")
    public Mono<?> login(@RequestBody UserDto request) {
        return customUserDetailsService.findByUsername(request.getUsername())
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails.getUsername());
                        return Mono.just(token);
                    } else {
                        return Mono.error(new ResourceNotFoundException("Invalid credentials"));
                    }
                });
    }
}
