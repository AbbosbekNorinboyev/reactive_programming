package uz.brb.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.TeacherDto;
import uz.brb.reactive.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Mono<Teacher> create(TeacherDto teacherDto);

    Mono<TeacherDto> get(String teacherId);

    Mono<List<TeacherDto>> getAll(Integer page, Integer size);

    Mono<TeacherDto> update(String teacherId, TeacherDto teacherDto);

    Mono<Void> delete(String teacherId);

    Mono<List<TeacherDto>> getByAgeMoreThan(int age);
}
