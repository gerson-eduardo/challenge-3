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
    @Column(nullable = false)
    private String cpf;
    private Boolean approved;

    public Vote(Proposal proposal, String cpf, Boolean approved) {
        this.proposal = proposal;
        this.cpf = cpf;
        this.approved = approved;
    }
}
