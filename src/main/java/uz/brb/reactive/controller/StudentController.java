package uz.brb.reactive.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.brb.reactive.dto.StudentDto;
import uz.brb.reactive.entity.Student;
import uz.brb.reactive.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/create")
    public Mono<Student> create(@Valid @RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }

    @GetMapping("/get")
    public Mono<Student> getById(@RequestParam("id") String id) {
        return studentService.get(id);
    }

    @GetMapping("/getAll")
    public Flux<Student> getAll(@RequestParam("page") Integer page,
                                @RequestParam("size") Integer size) {
        return studentService.getAll(page, size);
    }

    @PutMapping("/update")
    public Mono<Student> update(@RequestParam("id") String id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/delete")
    public Mono<Void> delete(@RequestParam("id") String id) {
        return studentService.delete(id);
    }

    @GetMapping("/filter/age")
    public Flux<Student> getByAge(@RequestParam("minAge") int minAge) {
        return studentService.getByAgeMoreThan(minAge);
    }
}
