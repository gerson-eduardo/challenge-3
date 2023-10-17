package br.gerson.sousa.msvoting.model;

import jakarta.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Proposal proposal;
    private String cpf;
    private boolean vote;

    public Vote() {}

    public Vote(Proposal proposal, String cpf, boolean vote) {
        this.proposal = proposal;
        this.cpf = cpf;
        this.vote = vote;
    }

    public Vote(Long id, Proposal proposal, String cpf, boolean vote) {
        this.id = id;
        this.proposal = proposal;
        this.cpf = cpf;
        this.vote = vote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
