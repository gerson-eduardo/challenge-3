package br.gerson.sousa.msvoting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Proposal proposal;
    private String cpf;
    private boolean vote;

    public Vote(Proposal proposal, String cpf, boolean vote) {
        this.proposal = proposal;
        this.cpf = cpf;
        this.vote = vote;
    }
}
