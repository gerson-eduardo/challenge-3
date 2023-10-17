package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
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
    public ResponseEntity<String> create(@RequestBody SaveRoleDto dto){
        int status = service.create(dto);
        String message;
        if(status == 404){
            message = "User not found";
        }else if(status == 409) {
            message = "Role already created";
        }else if(status == 400) {
            message = "Invalid Request";
        }else {
            message = "Role created successfully";
        }
        return ResponseEntity.status(status).body(message);
    }

    @GetMapping("/role")
    public ResponseEntity<List<FindRoleDto>> findAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findAll());
    }

    @GetMapping("/role/id/{id}")
    public ResponseEntity<Role> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findById(id).get());
    }

    @GetMapping("/role/employee/{cpf}")
    public ResponseEntity<Role> findByCpf(@PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findByCpf(cpf).get());
    }


    @PutMapping("/role")
    public ResponseEntity<String> update(@RequestBody SaveRoleDto dto){
        int status = service.update(dto);
        String message;
        if(status == 404){
            message = "Employee not found";
        }else if(status == 400) {
            message = "Invalid request";
        }else {
            message = "Role updated successfully";
        }
        return ResponseEntity.status(status).body(message);
    }
    @DeleteMapping("/role/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role deleted successfully");
    }

    @DeleteMapping("/role/{cpf}")
    public ResponseEntity<String> deleteByCpf(@PathVariable String cpf){
        service.deleteByCpf(cpf);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role deleted successfully");
    }
}
