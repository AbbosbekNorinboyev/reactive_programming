package uz.brb.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.TeacherDto;
import uz.brb.reactive.entity.Teacher;

public interface TeacherService {
    Mono<Teacher> create(TeacherDto teacherDto);

    Mono<Teacher> get(String teacherId);

    Flux<Teacher> getAll(Integer page, Integer size);

    Mono<Teacher> update(String teacherId, TeacherDto teacherDto);

    Mono<Void> delete(String teacherId);

    Flux<Teacher> getByAgeMoreThan(int age);
}
