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

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public Mono<Teacher> create(TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        return teacherRepository.save(teacher);
    }

    @Override
    public Mono<Teacher> get(String teacherId) {
        return teacherRepository.findById(teacherId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Teacher not found: " + teacherId)));
    }

    @Override
    public Flux<Teacher> getAll(Integer page, Integer size) {
        int skip = page * size;
        return teacherRepository.findAll()
                .skip(skip)
                .take(size);
    }

    @Override
    public Mono<Teacher> update(String teacherId, TeacherDto teacherDto) {
        return teacherRepository.findById(teacherId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Teacher not found: " + teacherId)))
                .flatMap(teacherExisting -> {
                    teacherExisting.setFullName(teacherDto.getFullName());
                    teacherExisting.setAge(teacherDto.getAge());
                    return teacherRepository.save(teacherExisting);
                });
    }

    @Override
    public Mono<Void> delete(String teacherId) {
        return teacherRepository.deleteById(teacherId);
    }

    @Override
    public Flux<Teacher> getByAgeMoreThan(int age) {
        return teacherRepository.findByAgeGreaterThan(age);
    }
}
