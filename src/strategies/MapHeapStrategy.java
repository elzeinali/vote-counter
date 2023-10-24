package strategies;

import models.Candidate;
import models.VoteEntry;

import java.util.*;

public class MapHeapStrategy implements VoteHandlingStrategy {
    private final Map<Integer, Candidate> candidates = new HashMap<>();
    private final PriorityQueue<Candidate> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b.voteCount, a.voteCount));

    @Override
    public void addVoteEntry(VoteEntry voteEntry) {
        int candidateId = voteEntry.candidateId;
        Candidate candidate = this.candidates.getOrDefault(candidateId, new Candidate(candidateId, 0));
        candidate.voteCount++;
        this.candidates.put(candidateId, candidate);

        // Offer the updated candidate to the heap. Due to the nature of heaps,
        // it's non-trivial to update an element's position. So we lazily add
        // the candidate, and handle potential duplicates during retrieval.
        this.maxHeap.offer(candidate);
    }

    @Override
    public List<Candidate> getTop3Candidates() {
        // A set to ensure we're not getting duplicates due to lazy removal
        Set<Integer> seen = new HashSet<>();
        List<Candidate> top3 = new ArrayList<>();

        while (top3.size() < 3 && !maxHeap.isEmpty()) {
            Candidate topCandidate = maxHeap.poll();

            // When retrieving candidates, we compare the vote count of the
            // candidate from the heap with the definitive vote count from the map.
            // This ensures that we get the most recent vote count, and it also
            // helps in filtering out any outdated copies of the candidate that
            // were lazily added to the heap.
            if (topCandidate.voteCount == this.candidates.get(topCandidate.id).voteCount && !seen.contains(topCandidate.id)) {
                seen.add(topCandidate.id);
                top3.add(topCandidate);
            }
        }

        // After retrieval, we put the top candidates back into the heap to ensure
        // the heap remains populated and useful for subsequent top candidate requests.
        for (Candidate candidate : top3) {
            maxHeap.offer(this.candidates.get(candidate.id));
        }

        return top3;
    }
}
