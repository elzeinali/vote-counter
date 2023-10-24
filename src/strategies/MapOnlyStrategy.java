package strategies;

import models.Candidate;
import models.VoteEntry;

import java.util.*;
import java.util.stream.Collectors;

public class MapOnlyStrategy implements VoteHandlingStrategy {
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    @Override
    public void addVoteEntry(VoteEntry voteEntry) {
        int candidateId = voteEntry.candidateId;
        Candidate candidate = this.candidates.getOrDefault(candidateId, new Candidate(candidateId, 0));
        candidate.voteCount++;
        this.candidates.put(candidateId, candidate);
    }

    @Override
    public List<Candidate> getTop3Candidates() {
        return candidates.values()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c2.voteCount, c1.voteCount))
                .limit(3)
                .collect(Collectors.toList());
    }
}
