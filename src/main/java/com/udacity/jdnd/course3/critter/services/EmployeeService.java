package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long employeeId) throws ObjectNotFoundException {
        String error = "Employee dows not exist "+employeeId;
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(error));
    }

    public List<Employee> getEmployeesByAvailability(Set<EmployeeSkill> employeeSkills, DayOfWeek day){
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(day);
        List<Employee> employeeList = new ArrayList<>();

        for (Employee employee : employees){
            if (employee.getSkills().containsAll(employeeSkills))
                employeeList.add(employee);
        }
        return employeeList;
    }
}
