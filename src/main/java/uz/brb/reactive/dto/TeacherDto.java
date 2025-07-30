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
public class TeacherDto {
    @NotBlank(message = "fullName must not be null")
    private String fullName;
    @Min(value = 1, message = "age must be at least 1")
    private Integer age;
}
