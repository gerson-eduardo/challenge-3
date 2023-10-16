package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
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
        int status  = service.create(dto);
        String message;
        if (status == 409){
            message = "Employee already present!";
        }else{
            message = "Employee created!";
        }
        return ResponseEntity.status(status).body(message);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<FindEmployeeDto>> findAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll());
    }

    @GetMapping("/employee/id/{id}")
    public ResponseEntity<FindEmployeeDto> findByid(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findById(id));
    }

    @GetMapping("/employee/{cpf}")
    public ResponseEntity<FindEmployeeDto> findByCpf(@PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findByCpf(cpf));
    }

    @GetMapping("/employee/email/{email}")
    public ResponseEntity<FindEmployeeDto> findByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findByEmail(email));
    }

    @PutMapping("/employee")
    public ResponseEntity<String > update(@RequestBody SaveEmployeeDto dto){
        int status = service.update(dto);
        if(status == 204){
            return ResponseEntity.status(204).body("Employee not found!");
        }else{
            return ResponseEntity.status(200).body("Employee updated!");
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
