package com.pet.project.services;

import com.pet.project.transferobjects.requests.PetRequest;
import com.pet.project.transferobjects.responses.PetResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PetService {
    PetResponse createPet(PetRequest petRequest);
    PetResponse getPetById(long petId);
    ResponseEntity<PetResponse> updatePetById(long petId, PetRequest petRequest);
    ResponseEntity<PetResponse> patchPet(long petId, Map<String, Object> updates);
    List<PetResponse> listAllPets();
    void deletePetById(long petId);
}
