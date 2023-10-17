package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.model.Vote;
import br.gerson.sousa.msvoting.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    private VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository){
        this.repository = repository;
    }

    public void save(Vote vote){
        repository.save(vote);
    }

    public List<Vote> findAll(){
        return repository.findAll();
    }

    public List<Vote> findAllByProposal_Name(String name){
        return repository.findAllByProposal_Name(name);
    }

    public List<Vote> findAllByProposal_Id(Long id){
        return repository.findAllByProposal_Id(id);
    }

    public List<Vote> findAllByCpf(String cpf){
        return repository.findAllByCpf(cpf);
    }
}
