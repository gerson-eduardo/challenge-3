package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalService {

    private ProposalRepository repository;

    @Autowired
    public ProposalService(ProposalRepository repository){
        this.repository = repository;
    }

    public void save(SaveProposalDto dto){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Proposal proposal = dto.toModel();
        proposal.setCreationDate(now.format(formatter));
        repository.save(dto.toModel());
    }

    public List<SaveProposalDto> findALl(){
        List<SaveProposalDto> dtos = new ArrayList<>();
        for(Proposal proposal: repository.findAll()){
            dtos.add(new SaveProposalDto(proposal));
        }
        return dtos;
    }
    public List<Proposal> findAllByResult(){
        return repository.findAllByResult(true);
    }

    public SaveProposalDto findById(Long id){
        return new SaveProposalDto(repository.findById(id).get());
    }
    public SaveProposalDto findByName(String name){
        return new SaveProposalDto(repository.findByName(name).get());
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
    public void deleteByName(String name){repository.deleteByName(name);}
}
