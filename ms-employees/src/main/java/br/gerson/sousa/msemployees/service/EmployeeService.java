package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    @Transactional
    public void save(SaveEmployeeDto dto){
        repository.save(dto.toModel());
    }

    public List<FindEmployeeDto> findAll(){
        List<Employee> employees = repository.findAll();
        List<FindEmployeeDto> dtos = new ArrayList<>();
        for (Employee emp: employees){
            dtos.add(new FindEmployeeDto(emp));
        }
        return dtos;
    }
    public FindEmployeeDto findById(Long id){
        return new FindEmployeeDto(repository.findById(id).get());
    }

    public FindEmployeeDto findByEmail(String email){
        return new FindEmployeeDto(repository.findByEmail(email).get());
    }

    public FindEmployeeDto findByCpf(String cpf){
        return new FindEmployeeDto(repository.findByCpf(cpf).get());
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public void deleteByCpf(String cpf){
        repository.deleteByCpf(cpf);
    }
}