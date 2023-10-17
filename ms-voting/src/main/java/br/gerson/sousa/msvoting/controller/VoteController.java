package br.gerson.sousa.msvoting.controller;

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
    public ResponseEntity<String> create(@RequestBody Vote vote){
        service.save(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @GetMapping("/vote")
    public ResponseEntity<List<Vote>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/vote/proposal-name/{name}")
    public ResponseEntity<List<Vote>> findAllByProposal_Name(@@PathVariable String name){
        return ResponseEntity.ok().body(service.findAllByProposal_Name(name));
    }
}
