package br.gerson.sousa.msvoting.controller;

import br.gerson.sousa.msvoting.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
