package uz.brb.reactive.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.TeacherDto;
import uz.brb.reactive.entity.Teacher;
import uz.brb.reactive.exception.ResourceNotFoundException;
import uz.brb.reactive.mapper.TeacherMapper;
import uz.brb.reactive.repository.TeacherRepository;
import uz.brb.reactive.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public Mono<Teacher> create(TeacherDto teacherDto) {
        return Mono.just(teacherDto)
                .map(teacherMapper::toEntity)
                .flatMap(teacherRepository::save);
    }

    @Override
    public Mono<TeacherDto> get(String teacherId) {
        return teacherRepository.findById(teacherId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Teacher not found: " + teacherId)))
                .map(teacherMapper::toDto);
    }

    @Override
    public Mono<List<TeacherDto>> getAll(Integer page, Integer size) {
        int skip = page * size;
        return teacherRepository.findAll()
                .skip(skip)
                .take(size)
                .map(teacherMapper::toDto)
                .collectList();
    }

    @Override
    public Mono<TeacherDto> update(String teacherId, TeacherDto teacherDto) {
        return teacherRepository.findById(teacherId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Teacher not found: " + teacherId)))
                .flatMap(teacherExisting -> {
                    teacherExisting.setFullName(teacherDto.getFullName());
                    teacherExisting.setAge(teacherDto.getAge());
                    return teacherRepository.save(teacherExisting);
                })
                .map(teacherMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String teacherId) {
        return teacherRepository.findById(teacherId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Teacher not found: " + teacherId)))
                .flatMap(teacherRepository::delete);
    }

    @Override
    public Mono<List<TeacherDto>> getByAgeMoreThan(int age) {
        return teacherRepository.findByAgeGreaterThan(age)
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No teachers found older than age: " + age)))
                .map(teacherMapper::toDto)
                .collectList();
    }
}
