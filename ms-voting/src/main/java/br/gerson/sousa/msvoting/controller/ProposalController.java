package br.gerson.sousa.msvoting.controller;

import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProposalController {
    private ProposalService service;

    @Autowired
    public ProposalController(ProposalService service){
        this.service = service;
    }

    @PostMapping("/proposal")
    public ResponseEntity<String> create(@RequestBody SaveProposalDto dto){
        service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }


}
