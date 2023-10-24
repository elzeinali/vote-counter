package models;

public class Candidate {
    public int id;
    public int voteCount;

    public Candidate(int id, int count) {
        this.id = id;
        this.voteCount = count;
    }

    @Override
    public String toString() {
        return String.format("Candidate with id: %d and %d votes.", this.id, this.voteCount);
    }
}
