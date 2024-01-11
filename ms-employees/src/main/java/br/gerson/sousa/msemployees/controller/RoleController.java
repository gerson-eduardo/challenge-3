package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.ex.InvalidRoleException;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "role-controller")
public class RoleController {

    private RoleService service;

    @Autowired
    public RoleController(RoleService service){
        this.service = service;
    }

    @PostMapping("/role")
    @Operation(summary = "Creates a role inside the database", method = "POST")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Role created sucessfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Role already exists in the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<String> create(@RequestBody SaveRoleDto dto){
        if(!dto.getRole().equals("ADMIN") && !dto.getRole().equals("USER")){
            return ResponseEntity.status(400).body("Invalid role in the request");
        }
        try {
            service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully!");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(404).body("User not found");
        }catch(EntityConflictException e){
            return ResponseEntity.status(409).body("Role already created");
        }catch(InvalidRoleException e){
            return ResponseEntity.status(400).body("Invalid Request");
        }
    }

    @GetMapping("/role")
    @Operation(summary = "Find all roles inside the database", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Return all roles from the database"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<FindRoleDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/role/id/{id}")
    public ResponseEntity<FindRoleDto> findById(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(service.findById(id));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/role/employee/{cpf}")
    public ResponseEntity<FindRoleDto> findByCpf(@PathVariable String cpf){
        try {
            return ResponseEntity.ok().body(service.findByCpf(cpf));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/role")
    public ResponseEntity<String> update(@RequestBody SaveRoleDto dto){
        if(!dto.getRole().equals("ADMIN") && !dto.getRole().equals("USER")){
            return ResponseEntity.status(400).body("Invalid role in the request");
        }
        try {
            service.update(dto);
            return ResponseEntity.status(HttpStatus.OK).body("Role updated successfully!");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(404).body("Role not found");
        }catch(InvalidRoleException e){
            return ResponseEntity.status(400).body("Invalid ROLE type in request");
        }
    }
    @DeleteMapping("/role/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role deleted successfully");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }

    }

    @DeleteMapping("/role/{cpf}")
    public ResponseEntity<String> deleteByCpf(@PathVariable String cpf){
        try {
            service.deleteByCpf(cpf);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role deleted successfully");
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }

    }
}
