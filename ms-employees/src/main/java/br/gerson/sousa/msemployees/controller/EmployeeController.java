package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "employee-controller")
public class EmployeeController {

    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @PostMapping("/employee")
    @Operation(summary = "Creates an employee inside the database", method = "POST")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Employee created sucessfully"),
            @ApiResponse(responseCode = "409", description = "Employee already exists in the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<String > create(@RequestBody SaveEmployeeDto dto){
        try {
            service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created");
        }catch(EntityConflictException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Find all employees inside the database", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Return a list of all employees inside the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/employee")
    public ResponseEntity<List<FindEmployeeDto>> findAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll());
    }

    @Operation(summary = "Find an employee inside the database by their ID", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Employee found inside the database"),
            @ApiResponse(responseCode = "404", description = "Employee not found in the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/employee/id/{id}")
    public ResponseEntity<FindEmployeeDto> findByid(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(service.findById(id));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find an employee inside the database by their CPF", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Employee found inside the database"),
            @ApiResponse(responseCode = "404", description = "Employee not found in the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/employee/{cpf}")
    public ResponseEntity<FindEmployeeDto> findByCpf(@PathVariable String cpf){
        try {
            return ResponseEntity.ok().body(service.findByCpf(cpf));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find an employee inside the database by their email", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Employee found inside the database"),
            @ApiResponse(responseCode = "404", description = "Employee not found in the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/employee/email/{email}")
    public ResponseEntity<FindEmployeeDto> findByEmail(@PathVariable String email){
        try {
            return ResponseEntity.ok().body(service.findByEmail(email));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update an employee inside the database", method = "PUT")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Employee updated inside the database"),
            @ApiResponse(responseCode = "404", description = "Employee not found in the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
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
        try {
            service.deleteByCpf(cpf);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted successfully");
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/employee/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Employee deleted successfully");
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
