package utils;

import strategies.MapHeapStrategy;
import strategies.MapOnlyStrategy;
import strategies.VoteHandlingStrategy;

public class VoteHandlingStrategyFactory {
    public static VoteHandlingStrategy getStrategyFromString(String strategyType) throws IllegalArgumentException {
        VoteHandlingStrategyType type = VoteHandlingStrategyType.fromString(strategyType);

        return switch (type) {
            case MAP_ONLY -> new MapOnlyStrategy();
            case MAP_AND_HEAP -> new MapHeapStrategy();
            default -> throw new IllegalArgumentException("Cannot return proper VoteHandlingStrategy because strategyType is invalid.");
        };
    }
}
