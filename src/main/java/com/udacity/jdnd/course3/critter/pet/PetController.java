package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setBirth_date(petDTO.getBirthDate());
        pet.setPetType(petDTO.getType());

        Customer customer = null;
        try {
            customer = customerService.findById(petDTO.getOwnerId());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
        pet.setPet_owner(customer);

        if (petDTO.getId() != 0){
            try {
                pet = petService.getPetById(petDTO.getId());
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        Pet pet_new = null;
        try {
            pet_new = petService.savePet(pet);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        customer.getListpet().add(pet_new);
        customerService.saveCustomer(customer);

        petDTO.setId(pet.getId());

        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) throws ObjectNotFoundException {
        Pet pet = petService.getPetById(petId);
        PetDTO petDTO = getPetDTO(pet);
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getAllByOwnerId(ownerId);
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    private PetDTO getPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setBirthDate(pet.getBirth_date());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setOwnerId(pet.getPet_owner().getId());
        petDTO.setType(pet.getPetType());
        return petDTO;
    }
}
