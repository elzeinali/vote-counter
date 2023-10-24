# Vote Counter Application

This application is designed to handle vote entries from a text file following the format ```voter_id,candidate_id```. The system process them as a stream and keeps watching for changes in the file. Hence, the application supports adding new votes to the file while it is running. The application also supports fetching the top 3 candidates based on the number of votes they have received. The system also flags voters who have voted more than once.

## Running the Vote Counter Application

### Prerequisites

Ensure you have `Java` installed on your machine. You can verify this by running: ```java -version```

### Compilation

Navigate to the root directory of the project (`vote-counter`). To compile all the Java files and place the compiled `.class` files in the `out` directory, run:

```javac -d out src/*.java src/*/*.java```

### Execution

After compilation, you can run the `Main` class to start the application:

```bash
java -cp out Main [strategy]
```

#### Parameters

- `strategy`: (Optional) The strategy you wish to use for vote handling. Can be either `MAP_ONLY` or `MAP_AND_HEAP`. Defaults to `MAP_ONLY` if not provided.

## Approaches

There are two main strategies implemented in this application: `MAP_ONLY` and `MAP_AND_HEAP`. We'll use `n` as the number of unique votes in the file and `m` as the number of unique candidates to analyse time and space complexities. n is always greater than or equal to m.

### 1. Map Only (`MAP_ONLY`)

In this approach, votes are stored in a map where the key is the candidate ID and the value is a candidate object with its vote count.

**Pros**:

- Simplicity: Easy to understand and implement.
- Efficient Memory Usage: No additional structures are needed other than the map. Space complexity is `O(m)`.
- Efficient Insertion: Insertion is faster. Time complexity is `O(1)`.

**Cons**:

- Slower Retrieval: To get the top 3 candidates, the map needs to be sorted, which can be relatively slower; `O(mlog(m))`.

### 2. Map and Heap (`MAP_AND_HEAP`)

In this strategy, along with the map, a max heap (priority queue) is maintained to keep track of candidates based on their vote count.

**Pros**:

- Faster Retrieval: Allows for efficient retrieval of top 3 candidates without sorting; `O(log(n))`.
  
**Cons**:

- Complexity: Additional structure and synchronization between map and heap.
- Memory Overhead: Extra storage is needed for the heap. The heap size will be `O(n)` and the map size will be `O(m)`. Resulting in a total space complexity of `O(n)`.
- Slower Insertion: Insertion is slower since we add elements to the heap and cause a re-balancing. Time complexity is `O(log(n))`.

#### To Summarize

The `MAP_ONLY` approach has a time complexity for insertion of `O(1)` and `O(mlog(m))` for retrieval. It also has a space complexity of `O(m)`.

The `MAP_AND_HEAP` approach has a time complexity for insertion of `O(log(n))` and `O(log(n))` for retrieval. It also has a space complexity of `O(n)`.

## Why Implement Both?

Different scenarios may benefit from different strategies. The `MAP_ONLY` approach would be more efficient overall if the number of insertions vastly outnumbers the times the user asks for the top 3 candidates. This is because each insertion is quick, it only requires to update a count in a hashmap. It would also be more efficient if the number of unique candidates is much smaller than the number of votes. However, retrieval of the top 3 candidates would require sorting the candidates, which can be costly if done frequently. On the other hand, the `MAP_AND_HEAP` strategy is more efficient when the user frequently queries for the top candidates and if the difference between the number of candidates and votes is small. By maintaining a max-heap, this strategy allows for faster retrieval of the top candidates, at the expense of slightly slower insertions due to the need to maintain the heap's order. Additionally, the `MAP_AND_HEAP` approach incurs a higher memory overhead because of maintaining both a map and a heap containing all the votes. So, if space is a premium concern, then the `MAP_ONLY` approach would be the more efficient choice. In conclusion, the best strategy largely depends on the specific requirements and constraints of the use case at hand.

## Interacting with the Application

Once the application is running:

- **Fetching Top 3 Candidates**: Simply enter `3` in the console.
- **Exiting the Application**: Type `exit` in the console.
- **Adding New Votes**: Add new votes to the `votes.txt` file in the format `voter_id,candidate_id`. The application will automatically detect the changes and update the results accordingly. Please note, that the system will not take into account edited lines that have already been processed. It will only process new lines that have been added to the file. Also, the system will not process lines that are not in the correct format. The application will print an error message in the console if it encounters any of these issues.
- **File Location**: The application will look for the `votes.txt` file in the `input/` folder at the root of the project (`vote-counter`).