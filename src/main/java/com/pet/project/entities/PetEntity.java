package com.pet.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "pets", schema = "petdb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Size(max = 64)
    @NotNull
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Size(max = 64)
    @NotNull
    @Column(name = "species", nullable = false, length = 64)
    private String species;

    @Size(max = 64)
    @Column(name = "owner_name", length = 64)
    private String ownerName;

    @Column(name = "age")
    private Integer age;

}