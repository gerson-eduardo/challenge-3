package br.gerson.sousa.msvoting.repository;

import br.gerson.sousa.msvoting.common.VoteConstants;
import br.gerson.sousa.msvoting.model.Vote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msvoting.common.VoteConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteRepositoryTest {
    @Mock
    VoteRepository repository;

    @Test
    void save() {
        when(repository.save(VOTE)).thenReturn(VOTE);
        Vote vote = repository.save(VOTE);

        verify(repository, atLeast(1)).save(VOTE);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(VOTE_LIST);
        List<Vote> votes = repository.findAll();

        verify(repository, atLeast(1)).findAll();
    }

    @Test
    void findAllByProposal_Name() {
        when(repository.findAllByProposal_Name("Proposal 1")).thenReturn(VOTE_LIST);
        List<Vote> votes = repository.findAllByProposal_Name("Proposal 1");

        verify(repository, atLeast(1)).findAllByProposal_Name("Proposal 1");
    }

    @Test
    void findAllByProposal_Id() {
        when(repository.findAllByProposal_Id(1L)).thenReturn(VOTE_LIST);
        List<Vote> votes = repository.findAllByProposal_Id(1L);

        verify(repository, atLeast(1)).findAllByProposal_Id(1L);
    }

    @Test
    void findAllByCpf() {
        when(repository.findAllByCpf("48975632987")).thenReturn(VOTE_LIST);
        List<Vote> votes = repository.findAllByCpf("48975632987");

        verify(repository, atLeast(1)).findAllByCpf("48975632987");
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(VOTE));
        Optional<Vote> vote = repository.findById(1L);

        verify(repository, atLeast(1)).findById(1L);
    }

    @Test
    void findByProposal_NameAndAndCpf() {
        when(repository.findByProposal_NameAndAndCpf("Proposal 1", "49784369715")).thenReturn(Optional.of(VOTE));
        Optional<Vote> vote = repository.findByProposal_NameAndAndCpf("Proposal 1", "49784369715");

        verify(repository, atLeast(1)).findByProposal_NameAndAndCpf("Proposal 1", "49784369715");
    }

    @Test
    void deleteById() {
        repository.deleteById(1L);
        verify(repository, atLeast(1)).deleteById(1L);
    }

    @Test
    void deleteAllByCpf() {
        repository.deleteAllByCpf("79865419745");
        verify(repository, atLeast(1)).deleteAllByCpf("79865419745");
    }
}