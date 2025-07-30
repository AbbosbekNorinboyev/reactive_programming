package uz.brb.reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.TeacherDto;
import uz.brb.reactive.entity.Teacher;
import uz.brb.reactive.service.TeacherService;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/create")
    public Mono<Teacher> create(@RequestBody TeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

    @GetMapping("/get")
    public Mono<Teacher> get(@RequestParam("teacherId") String teacherId) {
        return teacherService.get(teacherId);
    }

    @GetMapping("/getAll")
    public Flux<Teacher> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return teacherService.getAll(page, size);
    }

    @PutMapping("/update")
    public Mono<Teacher> update(@RequestParam("teacherId") String teacherId,
                                @RequestBody TeacherDto teacherDto) {
        return teacherService.update(teacherId, teacherDto);
    }

    @DeleteMapping("/delete")
    public Mono<Void> delete(@RequestParam("teacherId") String teacherId) {
        return teacherService.delete(teacherId);
    }

    @GetMapping("/filter/age")
    public Flux<Teacher> getByAgeMoreThan(@RequestParam("age") int age) {
        return teacherService.getByAgeMoreThan(age);
    }
}
