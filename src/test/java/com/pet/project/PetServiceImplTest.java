package com.pet.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.project.entities.PetEntity;
import com.pet.project.repos.PetRepository;
import com.pet.project.services.impl.PetServiceImpl;
import com.pet.project.transferobjects.requests.PetRequest;
import com.pet.project.transferobjects.responses.PetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PetServiceImpl petService;

    private PetRequest petRequest;
    private PetEntity petEntity;
    private PetResponse petResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petRequest = PetRequest.builder()
                .name("Buddy")
                .species("Dog")
                .age(3)
                .ownerName("Alice")
                .build();

        petEntity = PetEntity.builder()
                .petId(1L)
                .name("Buddy")
                .species("Dog")
                .age(3)
                .ownerName("Alice")
                .build();

        petResponse = PetResponse.builder()
                .petId(1L)
                .name("Buddy")
                .species("Dog")
                .age(3)
                .build();
    }

    @Test
    void testCreatePet() {
        when(objectMapper.convertValue(petRequest, PetEntity.class)).thenReturn(petEntity);
        when(petRepository.save(petEntity)).thenReturn(petEntity);
        when(objectMapper.convertValue(petEntity, PetResponse.class)).thenReturn(petResponse);

        PetResponse result = petService.createPet(petRequest);

        assertThat(result).isEqualTo(petResponse);
        verify(petRepository).save(petEntity);
    }

    @Test
    void testGetPetByIdFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(petEntity));
        when(objectMapper.convertValue(petEntity, PetResponse.class)).thenReturn(petResponse);

        PetResponse result = petService.getPetById(1L);

        assertThat(result).isEqualTo(petResponse);
        verify(petRepository).findById(1L);
    }

    @Test
    void testGetPetByIdNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        PetResponse result = petService.getPetById(1L);

        assertThat(result).isNull();
        verify(petRepository).findById(1L);
    }

    @Test
    void testUpdatePetByIdFound() {
        PetRequest updateRequest = petRequest;

        when(petRepository.findById(1L)).thenReturn(Optional.of(petEntity));
        when(objectMapper.convertValue(updateRequest, PetEntity.class)).thenReturn(petEntity);
        when(petRepository.save(petEntity)).thenReturn(petEntity);
        when(objectMapper.convertValue(petEntity, PetResponse.class)).thenReturn(petResponse);

        ResponseEntity<PetResponse> response = petService.updatePetById(1L, updateRequest);

        assertThat(response.getBody()).isEqualTo(petResponse);
        verify(petRepository).save(petEntity);
    }

    @Test
    void testUpdatePetByIdNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<PetResponse> response = petService.updatePetById(1L, petRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        verify(petRepository).findById(1L);
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    void testPatchPet() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("age", 5);

        PetEntity existing = new PetEntity();
        existing.setPetId(1L);
        existing.setAge(4);

        PetEntity patched = new PetEntity();
        patched.setPetId(1L);
        patched.setAge(5);

        PetResponse response = new PetResponse();
        response.setPetId(1L);
        response.setAge(5);

        when(petRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(petRepository.save(existing)).thenReturn(patched);
        when(objectMapper.convertValue(patched, PetResponse.class)).thenReturn(response);

        ResponseEntity<PetResponse> result = petService.patchPet(1L, updates);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody().getAge()).isEqualTo(5);
    }

    @Test
    void testListAllPets() {
        PetEntity e1 = new PetEntity();
        e1.setPetId(1L);
        e1.setName("Fido");
        e1.setSpecies("Dog");
        e1.setAge(3);

        PetEntity e2 = new PetEntity();
        e2.setPetId(2L);
        e2.setName("Whiskers");
        e2.setSpecies("Cat");
        e2.setAge(2);

        when(petRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<PetResponse> result = petService.listAllPets();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPetId()).isEqualTo(1L);
        assertThat(result.get(1).getPetId()).isEqualTo(2L);
    }

    @Test
    void testDeletePetById() {
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePetById(1L);

        verify(petRepository, times(1)).deleteById(1L);
    }

}
