package br.gerson.sousa.msvoting.model;

import jakarta.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Proposal proposal;
    @ManyToOne
    private Employee employee;
    private boolean vote;

    public Vote() {}

    public Vote(Proposal proposal, Employee employee, boolean vote) {
        this.proposal = proposal;
        this.employee = employee;
        this.vote = vote;
    }

    public Vote(Long id, Proposal proposal, Employee employee, boolean vote) {
        this.id = id;
        this.proposal = proposal;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
