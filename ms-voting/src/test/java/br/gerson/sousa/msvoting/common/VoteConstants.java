package br.gerson.sousa.msvoting.common;
import br.gerson.sousa.msvoting.dto.VoteDto;
import br.gerson.sousa.msvoting.model.Vote;

import java.util.Arrays;
import java.util.List;

import static br.gerson.sousa.msvoting.common.ProposalConstants.PROPOSAL;
import static br.gerson.sousa.msvoting.common.ProposalConstants.PROPOSAL2;

public class VoteConstants {
    public static final Vote VOTE = new Vote(1L, PROPOSAL, "48987236578", true);
    public static final Vote VOTE2 = new Vote(2L, PROPOSAL2, "48987236578", true);
    public static final VoteDto VOTEDTO = new VoteDto(PROPOSAL.getName(), "48987236578", true);
    public static final List<Vote> VOTE_LIST = Arrays.asList(VOTE, VOTE2);
}
