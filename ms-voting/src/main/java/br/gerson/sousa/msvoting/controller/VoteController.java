package br.gerson.sousa.msvoting.controller;

import br.gerson.sousa.msvoting.dto.VoteDto;
import br.gerson.sousa.msvoting.ex.EntityNotFoundException;
import br.gerson.sousa.msvoting.ex.InvalidRoleException;
import br.gerson.sousa.msvoting.ex.TimeExceededException;
import br.gerson.sousa.msvoting.model.Vote;
import br.gerson.sousa.msvoting.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VoteController {
    private VoteService service;

    @Autowired
    public VoteController(VoteService service){
        this.service = service;
    }

    @PostMapping("/vote")
    public ResponseEntity<String> create(@RequestBody VoteDto dto){
        try {
            service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vote saved");
        }catch (EntityNotFoundException | InvalidRoleException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (TimeExceededException e){
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
    }

    @GetMapping("/vote")
    public ResponseEntity<List<VoteDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/vote/proposal-name/{name}")
    public ResponseEntity<List<VoteDto>> findAllByProposal_Name(@PathVariable String name){
        return ResponseEntity.ok().body(service.findAllByProposal_Name(name));
    }

    @GetMapping("/vote/proposal-id/{id}")
    public ResponseEntity<List<VoteDto>> findAllByProposal_Id(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findAllByProposal_Id(id));
    }

    @GetMapping("/vote/employee/{cpf}")
    public ResponseEntity<List<VoteDto>> findAllByCpf(@PathVariable String cpf){
        return ResponseEntity.ok().body(service.findAllByCpf(cpf));
    }
    @DeleteMapping("/vote/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            service.deleteById(id);
            return ResponseEntity.ok().body("Vote deleted");
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vote/employee/{cpf}")
    public ResponseEntity<String> deleteAllByCpf(@PathVariable String cpf){
        try {
            service.deleteAllByCpf(cpf);
            return ResponseEntity.ok().body("All votes deleted");
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
