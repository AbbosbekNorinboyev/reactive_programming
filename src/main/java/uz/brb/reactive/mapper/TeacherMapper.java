package uz.brb.reactive.mapper;

import org.springframework.stereotype.Component;
import uz.brb.reactive.dto.TeacherDto;
import uz.brb.reactive.entity.Teacher;

import java.util.List;

@Component
public class TeacherMapper {
    public Teacher toEntity(TeacherDto teacherDto) {
        return Teacher.builder()
                .fullName(teacherDto.getFullName())
                .age(teacherDto.getAge())
                .build();
    }

    public TeacherDto toDto(Teacher teacher) {
        return TeacherDto.builder()
                .fullName(teacher.getFullName())
                .age(teacher.getAge())
                .build();
    }

    public List<TeacherDto> dtoList(List<Teacher> teachers) {
        if (teachers != null && !teachers.isEmpty()) {
            return teachers.stream().map(this::toDto).toList();
        }
        return null;
    }

    public void update(TeacherDto dto, Teacher entity) {
        if (dto == null) {
            return;
        }
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getAge() != null) {
            entity.setAge(dto.getAge());
        }
    }
}
