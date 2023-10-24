import models.Candidate;
import services.VoteFileReader;
import services.VoteHandler;
import strategies.VoteHandlingStrategy;
import utils.VoteHandlingStrategyFactory;

import java.util.List;
import java.util.Scanner;



public class Main {
    static final String FILE_PATH = "input/votes.txt";
    static final String DEFAULT_STRATEGY = "MAP_ONLY";
    static final String GET_TOP_3_COMMAND = "3";
    static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) {
        String strategyType;
        if (args.length != 1) {
            System.out.println("No strategy was provided (MAP_ONLY, MAP_AND_HEAP). Using default strategy: MAP_ONLY");
            strategyType = DEFAULT_STRATEGY;
        }else{
            strategyType = args[0];
        }

        VoteFileReader reader = new VoteFileReader(FILE_PATH);
        VoteHandlingStrategy strategy = VoteHandlingStrategyFactory.getStrategyFromString(strategyType);
        VoteHandler voteHandler = new VoteHandler(strategy);

        reader.addObserver(voteHandler::addVoteEntry);

        new Thread(reader).start();

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                if (GET_TOP_3_COMMAND.equals(input)) {
                    List<Candidate> top3 = voteHandler.getTop3Candidates();
                    System.out.println("Top 3 candidates:");
                    for(Candidate candidate : top3){
                        System.out.println(candidate.toString());
                    }
                } else if (EXIT_COMMAND.equalsIgnoreCase(input)) {
                    reader.stop();
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                }
            }
        }).start();
    }
}