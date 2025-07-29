package uz.brb.reactive.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    private Long id;
    @NotBlank(message = "fullName must not be null")
    private String fullName;
    @NotBlank(message = "email must not be null")
    private String email;
    @Min(value = 1, message = "Age must be at least 1")
    private Integer age;
}
