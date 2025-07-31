package uz.brb.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.TeacherDto;
import uz.brb.reactive.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Mono<Teacher> create(TeacherDto teacherDto);

    Mono<Teacher> get(String teacherId);

    Mono<List<Teacher>> getAll(Integer page, Integer size);

    Mono<Teacher> update(String teacherId, TeacherDto teacherDto);

    Mono<Void> delete(String teacherId);

    Mono<List<Teacher>> getByAgeMoreThan(int age);
}
