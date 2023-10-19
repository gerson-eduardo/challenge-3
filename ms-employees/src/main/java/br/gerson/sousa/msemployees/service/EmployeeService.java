package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.mapper.EmployeeMapper;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private EmployeeMapper mapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository, EmployeeMapper mapper){
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Transactional
    public int create(SaveEmployeeDto dto){
        if(employeeRepository.findByCpf(dto.getCpf()).isPresent()){
            return 409;
        }else{
            Employee emp = dto.toModel();
            employeeRepository.save(emp);
            roleRepository.save(new Role(emp, "USER"));
            return 201;
        }
    }

    public List<FindEmployeeDto> findAll(){
        List<Employee> employees = employeeRepository.findAll();
        List<FindEmployeeDto> dtos = new ArrayList<>();
        for (Employee emp: employees){
            dtos.add(new FindEmployeeDto(emp));
        }
        return dtos;
    }
    public FindEmployeeDto findById(Long id){
        Optional<Employee> emp = employeeRepository.findById(id);
        if(emp.isEmpty()){
            throw new EntityNotFoundException("Employee with id " + id + " not found!");
        }else {
            return new FindEmployeeDto(emp.get());
        }
    }

    public FindEmployeeDto findByEmail(String email){
        Optional<Employee> emp = employeeRepository.findByEmail(email);
        if(emp.isEmpty()){
            throw new EntityNotFoundException("Employee with email " + email + " not found!");
        }else {
            return new FindEmployeeDto(emp.get());
        }
    }

    public FindEmployeeDto findByCpf(String cpf){
        Optional<Employee> emp = employeeRepository.findByCpf(cpf);
        if(emp.isEmpty()){
            throw new EntityNotFoundException("Employee with cpf " + cpf + " not found!");
        }else {
            return new FindEmployeeDto(emp.get());
        }
    }

    @Transactional
    public int update(SaveEmployeeDto dto){
        Optional<Employee> emp = employeeRepository.findByCpf(dto.getCpf());
        if(emp.isEmpty()){
            return 204;
        }else{
            mapper.updateEmployeeFromSaveEmployeeDto(dto, emp.get());
            employeeRepository.save(emp.get());
            return 200;
        }
    }

    @Transactional
    public void deleteById(Long id){
        try {
            employeeRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Employee with id " + id + " not found!");
        }
    }

    @Transactional
    public void deleteByCpf(String cpf){
        try {
            employeeRepository.deleteByCpf(cpf);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Employee with cpf " + cpf + " not found!");
        }
    }
}