package br.gerson.sousa.msvoting.dto;

import br.gerson.sousa.msvoting.model.Proposal;

public class FindProposalDto {
    private String name;
    private String description;
    private String creationDate;
    private String endingDate;

    public FindProposalDto(){}

    public FindProposalDto(String name, String description, String creationDate, String endingDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.endingDate = endingDate;
    }

    public Proposal toModel(){
        return new Proposal(
                name,
                description,
                creationDate,
                endingDate
        );
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
