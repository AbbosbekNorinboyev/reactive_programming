package uz.brb.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import uz.brb.reactive.entity.AuthUser;

public interface AuthUserRepository extends ReactiveMongoRepository<AuthUser, Long> {

    Mono<AuthUser> findByUsername(String username);
}
