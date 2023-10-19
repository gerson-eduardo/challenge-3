package br.gerson.sousa.msvoting.service;

import br.gerson.sousa.msvoting.dto.FindProposalDto;
import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.ex.EntityConflictException;
import br.gerson.sousa.msvoting.ex.EntityNotFoundException;
import br.gerson.sousa.msvoting.model.DateFormatter;
import br.gerson.sousa.msvoting.model.Proposal;
import br.gerson.sousa.msvoting.model.Vote;
import br.gerson.sousa.msvoting.repository.ProposalRepository;
import br.gerson.sousa.msvoting.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void startPoll(String proposalName, String cpf, Duration duration){
        Optional<Proposal> proposal = proposalRepository.findByName(proposalName);
        if(proposal.isEmpty()){
            throw new EntityNotFoundException("Proposal with name " + proposalName + "not found!");
        }
        if(duration == null){
            duration = Duration.ofMinutes(1);
        }
        LocalDateTime endingTime = LocalDateTime.now().plus(duration);
        proposal.get().setEndingDate(formatter.dateToString(endingTime));
        proposalRepository.save(proposal.get());
    }

    @Transactional
    public void endPoll(String proposalName, String cpf){
        Optional<Proposal> proposal = proposalRepository.findByName(proposalName);
        if(proposal.isEmpty()) {
            throw new EntityNotFoundException("Proposal with name " + proposalName + "not found!");
        }
        boolean approved = countVotes(proposal.get());
        proposal.get().setApproved(approved);
        proposalRepository.save(proposal.get());

    }


    @Transactional
    public void save(SaveProposalDto dto){
        if(proposalRepository.findByName(dto.getName()).isPresent()){
            throw new EntityConflictException("Proposal already exists!");
        }else{
            LocalDateTime now = LocalDateTime.now();
            Proposal proposal = dto.toModel();
            proposal.setCreationDate(formatter.dateToString(now));
            proposalRepository.save(proposal);
        }
    }

    public List<FindProposalDto> findALl(){
        List<FindProposalDto> dtos = new ArrayList<>();
        for(Proposal proposal: proposalRepository.findAll()){
            dtos.add(new FindProposalDto(proposal));
        }return dtos;
    }
    public List<FindProposalDto> findAllByResult(boolean result){
        List<FindProposalDto> dtos = new ArrayList<>();
        for(Proposal proposal: proposalRepository.findAllByApproved(result)){
            dtos.add(new FindProposalDto(proposal));
        }return dtos;
    }

    public FindProposalDto findById(Long id){
        Optional<Proposal> proposal = proposalRepository.findById(id);
        if(proposal.isEmpty()){
            throw new EntityNotFoundException("Proposal with id " + id + " not found");
        }else{
            return new FindProposalDto(proposal.get());
        }
    }
    public FindProposalDto findByName(String name){
        Optional<Proposal> proposal = proposalRepository.findByName(name);
        if(proposal.isEmpty()){
            throw new EntityNotFoundException("Proposal with " + name + " not found");
        }else{
            return new FindProposalDto(proposal.get());
        }
    }

    @Transactional
    public void deleteById(Long id){
        try {
            proposalRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Proposal with id " + id + " not found");
        }
    }
    @Transactional
    public void deleteByName(String name) {
        try {
            proposalRepository.deleteByName(name);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Proposal with name " + name + " not found");
        }
    }

    boolean countVotes(Proposal proposal){
        List<Vote> votes = voteRepository.findAllByProposal_Name(proposal.getName());
        int yes = 0;
        int no  = 0;
        for(Vote vote : votes){
            if(vote.getApproved() == true){yes++;}
            else{no++;}
        }
        int result =  yes - no;
        return result > 0;
    }
}
