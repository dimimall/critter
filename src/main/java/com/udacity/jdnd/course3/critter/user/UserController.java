package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;
    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        List<Long> petsIds =  customerDTO.getPetIds();
        if (petsIds != null)
        {
            List<Pet> pets = petService.getAllPetsByIds(petsIds);
            customer.setListpet(pets);
        }

        Customer customer_new = customerService.saveCustomer(customer);

        customerDTO.setId(customer_new.getId());

        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOS = customers.stream().map(this::convertCustomerToCustomerDTO).collect(Collectors.toList());
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) throws ObjectNotFoundException {
        Pet pet = petService.getPetById(petId);
        Customer customer = pet.getPet_owner();
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());

        Set<DayOfWeek> daysAvailable = employeeDTO.getDaysAvailable();
        List<DayOfWeek> daysAvailableList;
        if (daysAvailable != null)
        {
            daysAvailableList = new ArrayList<>(daysAvailable);
        }
        else {
            daysAvailableList = new ArrayList<>();
        }
        employee.setDaysAvailable(daysAvailableList);

        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        List<EmployeeSkill> skillsList;

        if (skills != null)
        {
            skillsList = new ArrayList<>(skills);
        }
        else {
            skillsList = new ArrayList<>();
        }
        employee.setSkills(skillsList);

        Employee employee_new = employeeService.saveEmployee(employee);
        employeeDTO.setId(employee_new.getId());

        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws ObjectNotFoundException {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws ObjectNotFoundException {
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<DayOfWeek> dayOfWeeks = new ArrayList<>(daysAvailable);
        employee.setDaysAvailable(dayOfWeeks);
        employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeesByAvailability(employeeDTO.getSkills(),employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOS = employees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
        return employeeDTOS;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(new HashSet<>(employee.getSkills()));
        employeeDTO.setDaysAvailable(new HashSet<>(employee.getDaysAvailable()));

        return employeeDTO;

    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());

        List<Long> petIds;

        if(customer.getListpet() !=null){
            petIds = customer.getListpet().stream().map(Pet::getId).collect(Collectors.toList());
        } else {
            petIds = new ArrayList<>();
        }

        customerDTO.setPetIds(petIds);

        return customerDTO;
    }
}
