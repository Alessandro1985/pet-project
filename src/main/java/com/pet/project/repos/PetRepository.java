package com.pet.project.repos;

import com.pet.project.entities.PetEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<PetEntity, Long>  {
    List<PetEntity> findAll();
    Optional<PetEntity> findById(@NonNull Long id);
    void deleteById(@NonNull Long id);
}
