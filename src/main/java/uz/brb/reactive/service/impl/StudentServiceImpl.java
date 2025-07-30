package uz.brb.reactive.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.StudentDto;
import uz.brb.reactive.entity.Student;
import uz.brb.reactive.exception.ResourceNotFoundException;
import uz.brb.reactive.mapper.StudentMapper;
import uz.brb.reactive.repository.StudentRepository;
import uz.brb.reactive.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Mono<Student> create(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        return studentRepository.save(student);
    }

    @Override
    public Mono<Student> get(String studentId) {
        return studentRepository.findById(studentId).switchIfEmpty(
                Mono.error(new ResourceNotFoundException("Student not found: " + studentId))
        );
    }

    @Override
    public Flux<Student> getAll(Integer page, Integer size) {
        int skip = page * size;
        return studentRepository.findAll()
                .skip(skip)
                .take(size);
    }

    @Override
    public Mono<Student> update(String id, Student student) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Student not found: " + id)))
                .flatMap(studentExisting -> {
                    studentExisting.setFullName(student.getFullName());
                    studentExisting.setEmail(student.getEmail());
                    studentExisting.setAge(student.getAge());
                    return studentRepository.save(studentExisting);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return studentRepository.deleteById(id);
    }

    @Override
    public Flux<Student> getByAgeMoreThan(int age) {
        return studentRepository.findByAgeGreaterThan(age);
    }
}
