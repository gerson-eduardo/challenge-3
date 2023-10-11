package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    private RoleService service;

    @Autowired
    public RoleController(RoleService service){
        this.service = service;
    }

    @PostMapping("/role")
    public ResponseEntity<String> save(@RequestBody SaveRoleDto dto){
        service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> findAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll());
    }

    @GetMapping("/role/id/{id}")
    public ResponseEntity<Role> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findById(id).get());
    }

    @GetMapping("/role/employee/{cpf}")
    public ResponseEntity<Role> findById(@PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findByEmployee(cpf).get());
    }
}
