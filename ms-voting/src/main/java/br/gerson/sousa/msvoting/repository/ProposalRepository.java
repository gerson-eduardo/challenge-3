package br.gerson.sousa.msvoting.repository;

import br.gerson.sousa.msvoting.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Proposal save(Proposal proposal);

    List<Proposal> findAll();
    List<Proposal> findAllByApproved(boolean result);

    Optional<Proposal> findById(Long id);
    Optional<Proposal> findByName(String name);

    void deleteById(Long id);
    void deleteByName(String name);
}
