package models;

public class VoteEntry {
    public final int voterId;
    public final int candidateId;

    public VoteEntry(int voterId, int candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    /**
     * Static method to create a models.VoteEntry from a formatted vote entry string.
     *
     * @param line The string representation of the vote - (voter_id, candidate_id).
     * @return A models.VoteEntry if the line is valid, null otherwise.
     */
    public static VoteEntry fromString(String line) throws IllegalArgumentException {
        String[] parts = line.split(",");
        if (parts.length != 2) throw new IllegalArgumentException(String.format("Invalid vote entry format: %s", line));

        try {
            int voterId = Integer.parseInt(parts[0].trim());
            int candidateId = Integer.parseInt(parts[1].trim());
            return new VoteEntry(voterId, candidateId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid number format on line: %s", line));
        }
    }
}
