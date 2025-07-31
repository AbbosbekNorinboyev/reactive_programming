package uz.brb.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.StudentDto;
import uz.brb.reactive.entity.Student;

import java.util.List;

public interface StudentService {
    Mono<Student> create(StudentDto studentDto);

    Mono<Student> get(String studentId);

    Mono<List<Student>> getAll(Integer page, Integer size);

    Mono<Student> update(String id, Student student);

    Mono<Void> delete(String id);

    Mono<List<Student>> getByAgeMoreThan(int age);
}
