package uz.brb.reactive.mapper;

import org.springframework.stereotype.Component;
import uz.brb.reactive.dto.StudentDto;
import uz.brb.reactive.entity.Student;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentMapper {
    public Student toEntity(StudentDto studentDto) {
        return Student.builder()
                .fullName(studentDto.getFullName())
                .email(studentDto.getEmail())
                .age(studentDto.getAge())
                .build();
    }

    public StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .age(student.getAge())
                .build();
    }

    public List<StudentDto> dtoList(List<Student> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(StudentDto dto, Student entity) {
        if (dto == null) {
            return;
        }
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getAge() != null) {
            entity.setAge(dto.getAge());
        }
    }
}
