package br.gerson.sousa.msvoting.common;

import br.gerson.sousa.msvoting.dto.FindProposalDto;
import br.gerson.sousa.msvoting.dto.SaveProposalDto;
import br.gerson.sousa.msvoting.model.Proposal;

import java.util.Arrays;
import java.util.List;

public class ProposalConstants {
    public static final Proposal PROPOSAL = new Proposal(1L, "Proposal 1", "This is the proposal x", "", "", true);
    public static final Proposal PROPOSAL2 = new Proposal(1L, "Proposal 2", "This is the proposal x", "", "", false);
    public static final SaveProposalDto S_PROP_DTO = new SaveProposalDto("Proposal 1", "This is the proposal x");
    public static final FindProposalDto F_PROP_DTO = new FindProposalDto(PROPOSAL);
    public static final List<Proposal> PROPOSAL_LIST = Arrays.asList(PROPOSAL, PROPOSAL2);
}
