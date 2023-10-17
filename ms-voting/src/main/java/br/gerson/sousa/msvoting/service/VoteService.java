package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository){
        this.repository = repository;
    }


}
