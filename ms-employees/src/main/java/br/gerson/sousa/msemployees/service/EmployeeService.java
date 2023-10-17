package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.mapper.EmployeeMapper;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new FindEmployeeDto(employeeRepository.findById(id).get());
    }

    public FindEmployeeDto findByEmail(String email){
        return new FindEmployeeDto(employeeRepository.findByEmail(email).get());
    }

    public FindEmployeeDto findByCpf(String cpf){
        return new FindEmployeeDto(employeeRepository.findByCpf(cpf).get());
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

    public void deleteById(Long id){
        employeeRepository.deleteById(id);
    }

    public void deleteByCpf(String cpf){
        employeeRepository.deleteByCpf(cpf);
    }
}