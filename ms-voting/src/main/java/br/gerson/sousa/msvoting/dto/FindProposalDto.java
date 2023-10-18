package br.gerson.sousa.msvoting.dto;

import br.gerson.sousa.msvoting.model.Proposal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindProposalDto {
    private String name;
    private String description;
    private String creationDate;
    private String endingDate;
    private Boolean approved;

    public FindProposalDto(Proposal proposal){
        this.name = proposal.getName();
        this.description = proposal.getDescription();
        this.creationDate = proposal.getCreationDate();
        this.endingDate = proposal.getEndingDate();
        this.approved = proposal.getApproved();
    }
}
