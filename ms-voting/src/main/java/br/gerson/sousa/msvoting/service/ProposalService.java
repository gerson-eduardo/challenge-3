package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.dto.ProposalDto;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {

    private ProposalRepository repository;

    @Autowired
    public ProposalService(ProposalRepository repository){
        this.repository = repository;
    }

    public void save(ProposalDto dto){
        repository.save(dto.toModel());
    }

    public List<ProposalDto> findALl(){
        List<ProposalDto> dtos = new ArrayList<>();
        for(Proposal proposal: repository.findAll()){
            dtos.add(new ProposalDto(proposal));
        }
        return dtos;
    }

    public ProposalDto findById(Long id){
        return new ProposalDto(repository.findById(id).get());
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
