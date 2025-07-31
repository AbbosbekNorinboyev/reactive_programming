package uz.brb.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.StudentDto;
import uz.brb.reactive.entity.Student;

import java.util.List;

public interface StudentService {
    Mono<Student> create(StudentDto studentDto);

    Mono<StudentDto> get(String studentId);

    Mono<List<StudentDto>> getAll(Integer page, Integer size);

    Mono<StudentDto> update(String id, Student student);

    Mono<Void> delete(String id);

    Mono<List<StudentDto>> getByAgeMoreThan(int age);
}
