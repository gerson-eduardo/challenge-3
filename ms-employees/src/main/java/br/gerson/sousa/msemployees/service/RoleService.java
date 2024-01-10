package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.ex.InvalidRoleException;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, EmployeeRepository employeeRepository){
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void create(SaveRoleDto dto){
        Optional<Employee> emp = employeeRepository.findByCpf(dto.getCpf());
        if(emp.isEmpty()){
            throw new EntityNotFoundException("Role with cpf " + dto.getCpf() + " not found!");
        }else if(roleRepository.findByEmployee_Cpf(dto.getCpf()).isPresent()){
            throw new EntityConflictException("Role with cpf " + dto.getCpf() + "already present!");
        }else if(!dto.getRole().equals("ADMIN") && !dto.getRole().equals("USER")){
            throw new InvalidRoleException("Invalid role found in request. ROLE: " + dto.getRole());
        }
        roleRepository.save(new Role(emp.get(), dto.getRole()));
    }

    public List<FindRoleDto> findAll(){
        return roleRepository.findAll()
                .stream()
                .map(FindRoleDto::new)
                .collect(Collectors.toList());
    }

    public FindRoleDto findById(Long id){
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new EntityNotFoundException("Role with id " + id + " not found!");
        }
        return new FindRoleDto(role.get());
    }

    public FindRoleDto findByCpf(String cpf){
        Optional<Role> role = roleRepository.findByEmployee_Cpf(cpf);
        if(role.isEmpty()){
            throw new EntityNotFoundException("Role with employee cpf " + cpf + " not found!");
        }
        return new FindRoleDto(role.get());
    }

    @Transactional
    public void update(SaveRoleDto dto){
        Optional<Role> role = roleRepository.findByEmployee_Cpf(dto.getCpf());
        if(role.isEmpty()){
            throw new EntityNotFoundException("Role with cpf " + "not found!");
        }else if(!dto.getRole().equals("ADMIN") && !dto.getRole().equals("USER")){
            throw new InvalidRoleException("Invalid role found in request. ROLE: " + dto.getRole());
        }else{
            role.get().setRole(dto.getRole());
            roleRepository.save(role.get());
        }
    }

    @Transactional
    public void deleteById(Long id){
        try {
            roleRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Role with id " + id + " not found!");
        }
    }

    @Transactional
    public void deleteByCpf(String cpf){
        try {
            roleRepository.deleteByEmployee_Cpf(cpf);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Role with cpf " + cpf + " not found!");
        }
    }
}
