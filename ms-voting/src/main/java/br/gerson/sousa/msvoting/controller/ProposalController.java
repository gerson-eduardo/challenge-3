package br.gerson.sousa.msvoting.controller;

import br.gerson.sousa.msvoting.dto.FindProposalDto;
import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.ex.EntityConflictException;
import br.gerson.sousa.msvoting.ex.EntityNotFoundException;
import br.gerson.sousa.msvoting.ex.InvalidRoleException;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProposalController {
    private ProposalService service;

    @Autowired
    public ProposalController(ProposalService service){
        this.service = service;
    }

    @PostMapping("/proposal/name/{name}/start-poll")
    public ResponseEntity<String > startPoll(@PathVariable String name, @RequestParam String cpf, @RequestParam(required = false)Duration duration){
        try {
            service.startPoll(name, cpf, duration);
            return ResponseEntity.ok().body("Poll created");
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (InvalidRoleException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/proposal/name/{name}/end-poll")
    public ResponseEntity<String > endPoll(@PathVariable String name, @RequestParam String cpf){
        try {
            String message = service.endPoll(name, cpf);
            return ResponseEntity.ok().body(message);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (InvalidRoleException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/proposal")
    public ResponseEntity<String> create(@RequestBody SaveProposalDto dto, @RequestParam String cpf){
        try {
            service.save(dto, cpf);
            return ResponseEntity.status(HttpStatus.CREATED).body("Proposal created");
        }catch(EntityConflictException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }catch (InvalidRoleException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/proposal")
    public ResponseEntity<List<FindProposalDto>> findAll(){
        return ResponseEntity.ok().body(service.findALl());
    }

    @GetMapping("/proposal/result/{result}")
    public ResponseEntity<List<FindProposalDto>> findAllByResult(@PathVariable boolean result){
        return ResponseEntity.ok().body(service.findAllByResult(result));
    }

    @GetMapping("/proposal/id/{id}")
    public ResponseEntity<FindProposalDto> findById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(service.findById(id));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/proposal/name/{name}")
    public ResponseEntity<FindProposalDto> findByName(@PathVariable String name){
        try {
            return ResponseEntity.ok().body(service.findByName(name));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/proposal/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            service.deleteById(id);
            return ResponseEntity.ok().body("Proposal deleted");
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/proposal/name/{name}")
    public ResponseEntity<String> deleteById(@PathVariable String name){
        try {
            service.deleteByName(name);
            return ResponseEntity.ok().body("Proposal deleted");
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
