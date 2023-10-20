package br.gerson.sousa.msvoting.repository;

import br.gerson.sousa.msvoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote save(Vote vote);

    List<Vote> findAll();
    List<Vote> findAllByProposal_Name(String name);
    List<Vote> findAllByProposal_Id(Long id);
    List<Vote> findAllByCpf(String cpf);

    Optional<Vote> findById(Long id);
    Optional<Vote> findByProposal_NameAndAndCpf(String proposalName, String cpf);

    void deleteById(Long id);
    void deleteAllByCpf(String cpf);
}
