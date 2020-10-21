package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository)
    {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule saveSchedule(Schedule schedule)
    {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules()
    {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId)
    {
        return scheduleRepository.findAllPetById(petId);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId)
    {
        return scheduleRepository.findAllEmployeesById(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) throws ObjectNotFoundException {
        String error = "Customer does not exist "+customerId;
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ObjectNotFoundException(error));
        List<Pet> pets = customer.getListpet();

        List<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets)
        {
            schedules.addAll(scheduleRepository.findAllPetById(pet.getId()));
        }

        return schedules;
    }
}
