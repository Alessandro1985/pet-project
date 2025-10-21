package com.pet.project.services.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.project.entities.PetEntity;
import com.pet.project.repos.PetRepository;
import com.pet.project.services.PetService;
import com.pet.project.transferobjects.requests.PetRequest;
import com.pet.project.transferobjects.responses.PetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PetServiceImpl implements PetService {
    public static final String LOG_INFO_LISTING_ALL_PETS = "Retrieving all pets.";
    public static final String LOG_INFO_UPDATING_PET = "Updating pet.";
    public static final String LOG_INFO_GETTING_PET = "Getting pet.";
    public static final String LOG_INFO_CREATING_PET = "Creating pet.";
    public static final String LOG_INFO_DELETING_PET = "Deleting pet.";


    private final ObjectMapper objectMapper;
    private final PetRepository petRepository;

    public PetServiceImpl(ObjectMapper objectMapper, PetRepository petRepository) {
        this.objectMapper = objectMapper;
        this.petRepository = petRepository;
    }

    @Override
    public PetResponse createPet(PetRequest petRequest) {
        log.info(LOG_INFO_CREATING_PET);
        PetEntity petEntity = petRepository.save(objectMapper.convertValue(petRequest, PetEntity.class));
        return objectMapper.convertValue(petEntity, PetResponse.class);
    }

    @Override
    public PetResponse getPetById(long petId) {
        log.info(LOG_INFO_GETTING_PET);
        return petRepository.findById(petId)
                .map(existing -> {
                    return ResponseEntity.ok(objectMapper.convertValue(existing, PetResponse.class));
                })
                .orElse(ResponseEntity.notFound().build()).getBody();
    }

    @Override
    public ResponseEntity<PetResponse> updatePetById(long petId, PetRequest petRequest) {
        log.info(LOG_INFO_UPDATING_PET);
        return petRepository.findById(petId)
                .map(existing -> {
                    PetEntity toBeUpdated = objectMapper.convertValue(petRequest, PetEntity.class);
                    PetEntity entityUpdated = petRepository.save(toBeUpdated);
                    PetResponse response = objectMapper.convertValue(entityUpdated, PetResponse.class);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PetResponse> patchPet(long petId, Map<String, Object> updates) {
        log.info(LOG_INFO_UPDATING_PET);
        return petRepository.findById(petId)
                .map(existing -> {
                    try {
                        objectMapper.updateValue(existing, updates);
                    } catch (JsonMappingException e) {
                        throw new RuntimeException("Failed to apply patch updates", e);
                    }

                    PetEntity updated = petRepository.save(existing);

                    PetResponse response = objectMapper.convertValue(updated, PetResponse.class);

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public List<PetResponse> listAllPets() {
        List<PetEntity> petEntitiesList = petRepository.findAll();
        log.info(LOG_INFO_LISTING_ALL_PETS);
        return petEntitiesList.stream()
                .map(p -> PetResponse.builder()
                        .petId(p.getPetId())
                        .age(p.getAge())
                        .name(p.getName())
                        .species(p.getSpecies()).build())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deletePetById(long petId) {
        log.info(LOG_INFO_DELETING_PET);
        petRepository.deleteById(petId);
    }
}
