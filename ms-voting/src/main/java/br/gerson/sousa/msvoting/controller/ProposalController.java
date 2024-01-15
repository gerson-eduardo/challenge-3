package br.gerson.sousa.msvoting.controller;

import br.gerson.sousa.msvoting.dto.FindProposalDto;
import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.ex.EntityConflictException;
import br.gerson.sousa.msvoting.ex.EntityNotFoundException;
import br.gerson.sousa.msvoting.ex.InvalidRoleException;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.service.ProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "proposal-controller")
public class ProposalController {
    private ProposalService service;

    @Autowired
    public ProposalController(ProposalService service){
        this.service = service;
    }

    @PostMapping("/proposal/name/{name}/start-poll")
    @Operation(summary = "Start pool for the proposal", method = "POST")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Pool started successfully"),
            @ApiResponse(responseCode = "404", description = "Pool not found"),
            @ApiResponse(responseCode = "401", description = "Role unable to start pool")
    })
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
    @Operation(summary = "End pool for the proposal", method = "POST")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Pool ended successfully"),
            @ApiResponse(responseCode = "404", description = "Pool not found"),
            @ApiResponse(responseCode = "401", description = "Role unable to start pool")
    })
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
    @Operation(summary = "Create proposal", method = "POST")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Pool created successfully"),
            @ApiResponse(responseCode = "409", description = "Pool already exists"),
            @ApiResponse(responseCode = "401", description = "Role unable to start pool")
    })
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
    @Operation(summary = "Find all proposals", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Proposal list returned successfully"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<FindProposalDto>> findAll(){
        return ResponseEntity.ok().body(service.findALl());
    }

    @GetMapping("/proposal/result/{result}")
    @Operation(summary = "Find all proposals by result", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Proposal list returned successfully"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<FindProposalDto>> findAllByResult(@PathVariable boolean result){
        return ResponseEntity.ok().body(service.findAllByResult(result));
    }

    @GetMapping("/proposal/id/{id}")
    @Operation(summary = "Find proposal by id", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Proposal found successfully"),
            @ApiResponse(responseCode = "404", description = "Proposal not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<FindProposalDto> findById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(service.findById(id));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/proposal/name/{name}")
    @Operation(summary = "Find proposal by name", method = "GET")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Proposal found successfully"),
            @ApiResponse(responseCode = "404", description = "Proposal not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<FindProposalDto> findByName(@PathVariable String name){
        try {
            return ResponseEntity.ok().body(service.findByName(name));
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/proposal/id/{id}")
    @Operation(summary = "Delete proposal by id", method = "DELETE")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Proposal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Proposal not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            service.deleteById(id);
            return ResponseEntity.ok().body("Proposal deleted");
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/proposal/name/{name}")
    @Operation(summary = "Delete proposal by name", method = "DELETE")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Proposal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Proposal not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<String> deleteById(@PathVariable String name){
        try {
            service.deleteByName(name);
            return ResponseEntity.ok().body("Proposal deleted");
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
