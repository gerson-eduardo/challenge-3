package br.gerson.sousa.msvoting.dto;

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
}
