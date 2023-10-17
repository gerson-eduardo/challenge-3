package br.gerson.sousa.msvoting.dto;

import br.gerson.sousa.msvoting.model.Proposal;

public class ProposalDto {
    private String name;
    private String description;

    public ProposalDto(){}

    public ProposalDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProposalDto(Proposal proposal){
        this.name = proposal.getName();
        this.description = proposal.getDescription();
    }

    public Proposal toModel(){
        Proposal proposal = new Proposal();
        proposal.setName(this.name);
        proposal.setDescription(this.description);
        return proposal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }
}
