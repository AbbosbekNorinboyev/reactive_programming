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
import uz.brb.reactive.repository.TeacherRepository;
import uz.brb.reactive.service.StudentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public Mono<Student> create(StudentDto studentDto) {
        return teacherRepository.findById(studentDto.getTeacherId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Teacher not found: " + studentDto.getTeacherId())))
                .map(teacher -> {
                    Student studentFound = studentMapper.toEntity(studentDto);
                    studentFound.setTeacherId(teacher.getId());
                    return studentFound;
                })
                .flatMap(studentRepository::save);
    }

    @Override
    public Mono<StudentDto> get(String studentId) {
        return studentRepository.findById(studentId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Student not found: " + studentId)))
                .map(studentMapper::toDto);
    }

    @Override
    public Mono<List<StudentDto>> getAll(Integer page, Integer size) {
        int skip = page * size;
        return studentRepository.findAll()
                .skip(skip)
                .take(size)
                .map(studentMapper::toDto)
                .collectList();
    }

    @Override
    public Mono<StudentDto> update(String id, Student student) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Student not found: " + id)))
                .flatMap(studentExisting -> {
                    studentExisting.setFullName(student.getFullName());
                    studentExisting.setEmail(student.getEmail());
                    studentExisting.setAge(student.getAge());
                    return studentRepository.save(studentExisting);
                })
                .map(studentMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Student not found: " + id)))
                .flatMap(studentRepository::delete);
    }

    @Override
    public Mono<List<StudentDto>> getByAgeMoreThan(int age) {
        return studentRepository.findByAgeGreaterThan(age)
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No students found older than age: " + age)))
                .map(studentMapper::toDto)
                .collectList();
    }
}
