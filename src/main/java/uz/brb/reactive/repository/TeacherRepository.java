package uz.brb.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import uz.brb.reactive.entity.Teacher;

public interface TeacherRepository extends ReactiveMongoRepository<Teacher, String> {
    Flux<Teacher> findByAgeGreaterThan(int age);
}
