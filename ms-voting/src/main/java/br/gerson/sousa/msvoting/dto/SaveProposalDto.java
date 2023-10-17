package br.gerson.sousa.msvoting.dto;

import br.gerson.sousa.msvoting.model.Proposal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveProposalDto {
    private String name;
    private String description;

    public SaveProposalDto(Proposal proposal){
        this.name = proposal.getName();
        this.description = proposal.getDescription();
    }

    public Proposal toModel(){
        Proposal proposal = new Proposal();
        proposal.setName(this.name);
        proposal.setDescription(this.description);
        return proposal;
    }
}
