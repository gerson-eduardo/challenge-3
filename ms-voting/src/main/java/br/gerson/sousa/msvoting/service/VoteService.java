package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.dto.VoteDto;
import br.gerson.sousa.msvoting.model.DateFormatter;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.model.Vote;
import br.gerson.sousa.msvoting.repository.ProposalRepository;
import br.gerson.sousa.msvoting.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {
    private VoteRepository voteRepository;
    private ProposalRepository proposalRepository;
    private DateFormatter formatter = new DateFormatter();

    @Autowired
    public VoteService(VoteRepository voteRepository, ProposalRepository proposalRepository){
        this.voteRepository = voteRepository;
        this.proposalRepository = proposalRepository;
    }

    @Transactional
    public void save(VoteDto dto){
        Optional<Proposal> proposal = proposalRepository.findByName(dto.getName());
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(formatter.stringToDate(proposal.get().getEndingDate()))){
            voteRepository.save(new Vote(proposal.get(), dto.getCpf(), dto.isApproved()));
        }
    }

    public List<VoteDto> findAll(){
        List<VoteDto> dtos = new ArrayList();
        for(Vote vote: voteRepository.findAll()){
            dtos.add(new VoteDto(vote));
        }return dtos;
    }

    public List<VoteDto> findAllByProposal_Name(String name){
        List<VoteDto> dtos = new ArrayList();
        for(Vote vote: voteRepository.findAllByProposal_Name(name)){
            dtos.add(new VoteDto(vote));
        }return dtos;
    }

    public List<VoteDto> findAllByProposal_Id(Long id){
        List<VoteDto> dtos = new ArrayList();
        for(Vote vote: voteRepository.findAllByProposal_Id(id)){
            dtos.add(new VoteDto(vote));
        }return dtos;
    }

    public List<Vote> findAllByCpf(String cpf){
        return voteRepository.findAllByCpf(cpf);
    }

    @Transactional
    public void deleteById(Long id){voteRepository.deleteById(id);}

    @Transactional
    public void deleteAllByCpf(String cpf){voteRepository.deleteAllByCpf(cpf);}

}
