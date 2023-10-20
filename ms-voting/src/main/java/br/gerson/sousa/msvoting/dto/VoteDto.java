package br.gerson.sousa.msvoting.dto;

import br.gerson.sousa.msvoting.model.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteDto {
    private String name;
    private String cpf;
    private boolean approved;

    public VoteDto(Vote vote){
        this.name = vote.getProposal().getName();
        this.cpf = vote.getCpf();
        this.approved = vote.getApproved();
    }
}
