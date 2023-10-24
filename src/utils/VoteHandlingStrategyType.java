package utils;

public enum VoteHandlingStrategyType {
    MAP_ONLY("MAP_ONLY"),
    MAP_AND_HEAP("MAP_AND_HEAP");

    private final String strategyName;

    VoteHandlingStrategyType(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public static VoteHandlingStrategyType fromString(String strategyName) throws IllegalArgumentException {
        for (VoteHandlingStrategyType type : VoteHandlingStrategyType.values()) {
            if (type.getStrategyName().equalsIgnoreCase(strategyName)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid strategy type: %s. Valid types are Valid types are: MAP_ONLY, MAP_AND_HEAP.", strategyName));
    }
}
