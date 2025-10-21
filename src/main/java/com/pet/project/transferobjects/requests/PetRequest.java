package com.pet.project.transferobjects.requests;

import com.pet.project.transferobjects.PetDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest extends PetDetails {
    private String ownerName;
}
