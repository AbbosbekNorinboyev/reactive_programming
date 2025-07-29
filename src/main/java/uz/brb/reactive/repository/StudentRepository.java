package uz.brb.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import uz.brb.reactive.entity.Student;

public interface StudentRepository extends ReactiveMongoRepository<Student, Long> {
    Flux<Student> findByAgeGreaterThan(int age);
}
