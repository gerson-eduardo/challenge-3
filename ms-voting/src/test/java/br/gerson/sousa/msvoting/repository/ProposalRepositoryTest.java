package br.gerson.sousa.msvoting.repository;

import br.gerson.sousa.msvoting.model.Proposal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msvoting.common.ProposalConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProposalRepositoryTest {
    @Mock
    ProposalRepository repository;

    @Test
    void save() {
        when(repository.save(PROPOSAL)).thenReturn(PROPOSAL);
        Proposal prop = repository.save(PROPOSAL);

        verify(repository, atLeast(1)).save(PROPOSAL);
        assertEquals(PROPOSAL, prop);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(PROPOSAL_LIST);

        List<Proposal> propList = repository.findAll();

        verify(repository, atLeast(1)).findAll();
    }

    @Test
    void findAllByApproved() {
        when(repository.findAllByApproved(true)).thenReturn(PROPOSAL_LIST);

        List<Proposal> propList = repository.findAllByApproved(true);

        verify(repository, atLeast(1)).findAllByApproved(true);
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(PROPOSAL));

        Optional<Proposal> prop = repository.findById(1L);

        verify(repository, atLeast(1)).findById(1L);
    }

    @Test
    void findByName() {
        when(repository.findByName("Proposal 1")).thenReturn(Optional.of(PROPOSAL));

        Optional<Proposal> prop = repository.findByName("Proposal 1");

        verify(repository, atLeast(1)).findByName("Proposal 1");
    }

    @Test
    void deleteById() {
        repository.deleteById(1L);
        verify(repository, atLeast(1)).deleteById(1L);
    }

    @Test
    void deleteByName() {
        repository.deleteByName("Proposal 1");
        verify(repository, atLeast(1)).deleteByName("Proposal 1");
    }
}