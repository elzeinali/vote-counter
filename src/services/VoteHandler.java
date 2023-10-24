package services;

import models.Candidate;
import models.VoteEntry;
import strategies.VoteHandlingStrategy;

import java.util.*;

public class VoteHandler {
    private final Set<Integer> voterIds = new HashSet<>();
    private final VoteHandlingStrategy voteHandlingStrategy;

    public VoteHandler(VoteHandlingStrategy strategy) {
        this.voteHandlingStrategy = strategy;
    }

    public void addVoteEntry(VoteEntry voteEntry) {
        // Check for duplicate votes: Ensuring a voter doesn't vote more than once.
        // This deduplication logic is centralized here so all strategies can leverage it, ensuring consistency.
        if(this.voterIds.contains(voteEntry.voterId)) {
            System.err.printf("Voter with id: %d has already voted.%n", voteEntry.voterId);
            return;
        }else{
            this.voterIds.add(voteEntry.voterId);
        }
        this.voteHandlingStrategy.addVoteEntry(voteEntry);
    }

    public List<Candidate> getTop3Candidates() { return this.voteHandlingStrategy.getTop3Candidates(); }
}
