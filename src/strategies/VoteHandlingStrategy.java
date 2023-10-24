package strategies;

import models.Candidate;
import models.VoteEntry;

import java.util.List;

public interface VoteHandlingStrategy {
    void addVoteEntry(VoteEntry voteEntry);
    List<Candidate> getTop3Candidates();
}

