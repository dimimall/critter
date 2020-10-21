package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository)
    {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(Pet pet) throws ObjectNotFoundException {
        petRepository.save(pet);
        Customer owner = pet.getPet_owner();

        if (owner != null) {
            long customerId = owner.getId();
            Optional<Customer> customerOptional = customerRepository.findById(customerId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();

                if(customer.getListpet() != null) {
                    customer.getListpet().add(pet);
                }

                customerRepository.save(customer);
            }
        } else {
                throw new ObjectNotFoundException("Customer not found");
        }

        return pet;
    }

    public Pet getPetById(long id) throws ObjectNotFoundException {

        Pet pet;

        Optional<Pet> optionalPet = petRepository.findById(id);

        if(optionalPet.isPresent()){
            pet = optionalPet.get();
        }else {
            throw new ObjectNotFoundException("Pet not found By Id : " + id);
        }

        return pet;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllByOwnerId(long ownerId) {

        List<Pet> pets;

        Optional<Customer> customerOptional = customerRepository.findById(ownerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pets = customer.getListpet();
        } else {
            pets = new ArrayList<>();
        }

        return pets;
    }

    public List<Pet> getAllPetsByIds(List<Long> ids){
        return petRepository.findAllById(ids);
    }
}
