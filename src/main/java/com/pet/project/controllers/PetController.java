package com.pet.project.controllers;


import com.pet.project.services.PetService;
import com.pet.project.transferobjects.requests.PetRequest;
import com.pet.project.transferobjects.responses.PetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/pet")
public class PetController {

    private static final String BAD_REQUEST = "{ \"message\": \"Invalid request.\", \"details\": [ \"The request body contains wrong data.\" ] }";
    private static final String CREATED_PET_EXAMPLE = "{ \"name\": \"luke\", \"species\": \"dog\", \"age\": \"3\", \"petId\": \"1\"}";

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create Pet.",
            description = "Creates a pet based on the infor in the request body.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created the pet.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = CREATED_PET_EXAMPLE)})),
                    @ApiResponse(responseCode = "400", description = "Bad Request - The request body contains wrong data.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = BAD_REQUEST)})),
                    @ApiResponse(responseCode = "405", description = "Method not allowed.", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(mediaType = "application/json"))}
    )
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetRequest petRequestBody) {
        return ResponseEntity.ok(petService.createPet(petRequestBody));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "listAllPets",
            description = "Get the lit of all Pets.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of pets.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = CREATED_PET_EXAMPLE)})),
                    @ApiResponse(responseCode = "404", description = "Not found - The pet is not found.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = BAD_REQUEST)})),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(mediaType = "application/json"))}
    )
    public List<PetResponse> listAllPets() {
        return petService.listAllPets();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "findPerById",
            description = "Find a pet by id.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieved the pet.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = CREATED_PET_EXAMPLE)})),
                    @ApiResponse(responseCode = "404", description = "Not found - The pet is not found.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = BAD_REQUEST)})),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(mediaType = "application/json"))}
    )
    public PetResponse findPerById(@PathVariable("id") Long id) {
        return petService.getPetById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "patchPet",
            description = "Patch the pet by id.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully patched the pet.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = CREATED_PET_EXAMPLE)})),
                    @ApiResponse(responseCode = "404", description = "Not found - The pet is not found.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = BAD_REQUEST)})),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(mediaType = "application/json"))}
    )
    public ResponseEntity<PetResponse> patchPet(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return petService.patchPet(id, updates);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "updatePet",
            description = "Update the pet by id.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully updated the pet.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = CREATED_PET_EXAMPLE)})),
                    @ApiResponse(responseCode = "404", description = "Not found - The pet is not found.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = BAD_REQUEST)})),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(mediaType = "application/json"))}
    )
    public ResponseEntity<PetResponse> updatePet(@PathVariable @NonNull Long id, @RequestBody PetRequest petRequest) {
        return petService.updatePetById(id, petRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletePet",
            description = "Delete the pet by id.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully deleted the pet by id.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = CREATED_PET_EXAMPLE)})),
                    @ApiResponse(responseCode = "404", description = "Not found - The pet is not found.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = BAD_REQUEST)})),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(mediaType = "application/json"))}
    )
    public void deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
    }


}
