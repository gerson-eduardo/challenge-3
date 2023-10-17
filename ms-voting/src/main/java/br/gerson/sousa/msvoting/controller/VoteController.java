package br.gerson.sousa.msvoting.controller;

import br.gerson.sousa.msvoting.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VoteController {
    private VoteService service;

    @Autowired
    public VoteController(VoteService service){
        this.service = service;
    }
}
