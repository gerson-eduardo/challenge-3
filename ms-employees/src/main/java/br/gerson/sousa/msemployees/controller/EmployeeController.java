package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @PostMapping("/employee")
    public ResponseEntity<String > create(@RequestBody SaveEmployeeDto dto){
        try {
            service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created");
        }catch(EntityConflictException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/employee")
    public ResponseEntity<List<FindEmployeeDto>> findAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll());
    }

    @GetMapping("/employee/id/{id}")
    public ResponseEntity<FindEmployeeDto> findByid(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(service.findById(id));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/{cpf}")
    public ResponseEntity<FindEmployeeDto> findByCpf(@PathVariable String cpf){
        try {
            return ResponseEntity.ok().body(service.findByCpf(cpf));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/email/{email}")
    public ResponseEntity<FindEmployeeDto> findByEmail(@PathVariable String email){
        try {
            return ResponseEntity.ok().body(service.findByEmail(email));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/employee")
    public ResponseEntity<String > update(@RequestBody SaveEmployeeDto dto){
        try {
            service.update(dto);
            return ResponseEntity.status(HttpStatus.OK).body("Employee updated");
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{cpf}")
    public ResponseEntity<String> deleteByCpf(@PathVariable String  cpf){
        service.deleteByCpf(cpf);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted successfully");
    }

    @DeleteMapping("/employee/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted successfully");
    }
}
