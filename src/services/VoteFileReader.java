package services;

import models.VoteEntry;
import observer.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public class VoteFileReader extends Observable<VoteEntry> implements Runnable {
    private final Path path;
    private long lastReadPosition = 0; // Used to skip already read lines
    private long currentLineNumber = 0; // Used for error reporting
    private volatile boolean running = true;  // Flag to control the loop

    public VoteFileReader(String filePath) {
        this.path = Paths.get(filePath);
    }

    public void stop(){
        this.running = false;
    }

    @Override
    public void run() {
        readFile();

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (this.running) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        readFile();
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error while watching file for changes.");
            e.printStackTrace();
        }
    }

    private void readFile() {
        try (BufferedReader reader = Files.newBufferedReader(this.path)) {
            this.safeSkip(reader, this.lastReadPosition);

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                this.currentLineNumber++;

                try {
                    VoteEntry voteEntry = VoteEntry.fromString(currentLine);
                    notifyObservers(voteEntry);
                } catch (IllegalArgumentException e) {
                    System.err.printf("Ignoring invalid vote on line %d: %s.%n", this.currentLineNumber, currentLine);
                }

                lastReadPosition += currentLine.length() + 1; // +1 for the newline character
            }
        } catch (IOException e) {
            System.err.println("Error while watching file for changes.");
            e.printStackTrace();
        }
    }

    private void safeSkip(BufferedReader reader, long n) throws IOException {
        long remaining = n;
        while (remaining > 0) {
            long skipped = reader.skip(remaining);
            if (skipped == 0) break;
            remaining -= skipped;
        }
    }

}
