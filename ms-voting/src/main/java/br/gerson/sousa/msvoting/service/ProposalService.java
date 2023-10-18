package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.model.DateFormatter;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.model.Vote;
import br.gerson.sousa.msvoting.repository.ProposalRepository;
import br.gerson.sousa.msvoting.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProposalService {

    private ProposalRepository proposalRepository;
    private VoteRepository voteRepository;
    private DateFormatter formatter= new DateFormatter();

    @Autowired
    public ProposalService(ProposalRepository proposalRepository, VoteRepository voteRepository){
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
    }

    public void startPoll(String proposalName, String cpf, Duration duration){
        Proposal proposal = proposalRepository.findByName(proposalName).get();
        if(duration == null){
            duration = Duration.ofMinutes(1);
        }

        LocalDateTime endingTime = LocalDateTime.now().plus(duration);
        proposal.setEndingDate(formatter.dateToString(endingTime));
        proposalRepository.save(proposal);
    }
    public void endPoll(String proposalName, String cpf){
        Proposal proposal = proposalRepository.findByName(proposalName).get();
        if(proposal.getApproved() != null){
            boolean approved = countVotes(proposal);
            proposal.setApproved(approved);
            proposalRepository.save(proposal);
        }
    }


    @Transactional
    public void save(SaveProposalDto dto){
        LocalDateTime now = LocalDateTime.now();
        Proposal proposal = dto.toModel();
        proposal.setCreationDate(formatter.dateToString(now));
        proposalRepository.save(proposal);
    }

    public List<SaveProposalDto> findALl(){
        List<SaveProposalDto> dtos = new ArrayList<>();
        for(Proposal proposal: proposalRepository.findAll()){
            dtos.add(new SaveProposalDto(proposal));
        }
        return dtos;
    }
    public List<Proposal> findAllByResult(boolean result){
        return proposalRepository.findAllByApproved(result);
    }

    public SaveProposalDto findById(Long id){
        return new SaveProposalDto(proposalRepository.findById(id).get());
    }
    public SaveProposalDto findByName(String name){
        return new SaveProposalDto(proposalRepository.findByName(name).get());
    }

    @Transactional
    public void deleteById(Long id){
        proposalRepository.deleteById(id);
    }
    @Transactional
    public void deleteByName(String name){proposalRepository.deleteByName(name);}

    boolean countVotes(Proposal proposal){
        List<Vote> votes = voteRepository.findAllByProposal_Name(proposal.getName());
        int result = Collections.frequency(votes, true) - Collections.frequency(votes, false);
        if(result > 0){
            return true;
        }else {
            return false;
        }
    }
}
