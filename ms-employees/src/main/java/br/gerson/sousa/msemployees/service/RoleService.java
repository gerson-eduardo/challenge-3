package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public int create(SaveRoleDto dto){
        Optional<Employee> emp = employeeRepository.findByCpf(dto.getCpf());
        if(emp.isEmpty()){
            return 404;
        }else if(findByCpf(dto.getCpf()).isPresent()){
            return 409;
        }else if(!dto.getRole().equals("ADMIN") && !dto.getRole().equals("USER")){
            return 400;
        }else{
            roleRepository.save(new Role(emp.get(), dto.getRole()));
            return 201;
        }
    }

    public List<FindRoleDto> findAll(){
        List<Role> roles= roleRepository.findAll();
        List<FindRoleDto> dtos = new ArrayList<>();
        for(Role role: roles){
            dtos.add(new FindRoleDto(role));
        }
        return dtos;
    }

    public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
    }

    public Optional<Role> findByCpf(String cpf){
        return roleRepository.findByEmployee_Cpf(cpf);
    }

    @Transactional
    public int update(SaveRoleDto dto){
        Optional<Role> role = roleRepository.findByEmployee_Cpf(dto.getCpf());
        if(role.isEmpty()){
            return 404;
        }else if(!dto.getRole().equals("ADMIN") && !dto.getRole().equals("USER")){
            return 400;
        }else{
            role.get().setRole(dto.getRole());
            roleRepository.save(role.get());
            return 200;
        }
    }

    @Transactional
    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }

    @Transactional
    public void deleteByCpf(String cpf){
        roleRepository.deleteByEmployee_Cpf(cpf);
    }
}
