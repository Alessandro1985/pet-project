package com.pet.project.transferobjects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDetails {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String species;

    @Min(value = 0L, message = "The age must be positive.")
    private Integer age;

}
