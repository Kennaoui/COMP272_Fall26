# COMP 272/400C — Module 2 Programming Assignment

## Algorithm Analysis: Theory and Experimentation

### Submission

Submit the following two files:

1. `Module02Answers.pdf` — all written answers, tables, calculations, and discussion.
2. `RangeSumExperiment.java` — your completed Java program for Exercise 2.

Your explanations must identify the input size and justify every complexity claim. A bound without an explanation is not a complete answer.

---

## Exercise 1 — Analyze Four Functions

The four functions below are complete. You do **not** need to implement or execute them.

For each function:

1. Define the input size. Use more than one parameter when one parameter would not describe the input accurately.
2. Select an appropriate primitive operation.
3. Derive a worst-case cost function by counting that operation. You may use a summation, recurrence, or a formula involving multiple input parameters when appropriate.
4. Give a tight asymptotic upper bound for the worst case using $O$.
5. Determine the auxiliary-space complexity. If the method creates its result, report the result space separately from the auxiliary space.

### Function A — Common value

```java
public static boolean haveCommonValue(int[] first, int[] second) {
    for (int i = 0; i < first.length; i++) {
        for (int j = 0; j < second.length; j++) {
            if (first[i] == second[j]) {
                return true;
            }
        }
    }

    return false;
}
```

**Point to consider:** The two arrays do not necessarily have the same length.

### Function B — Increasing triples

```java
public static int countIncreasingTriples(int[] values) {
    int count = 0;

    for (int i = 0; i < values.length - 2; i++) {
        for (int j = i + 1; j < values.length - 1; j++) {
            for (int k = j + 1; k < values.length; k++) {
                if (values[i] < values[j]
                        && values[j] < values[k]) {
                    count++;
                }
            }
        }
    }

    return count;
}
```

Count the number of distinct index triples $(i,j,k)$ examined, where $i<j<k$. You may express the cost using a binomial coefficient before expanding it.

### Function C — Fast exponentiation

```java
public static long fastPower(long base, int exponent) {
    if (exponent == 0) {
        return 1;
    }

    long half = fastPower(base, exponent / 2);

    if (exponent % 2 == 0) {
        return half * half;
    }

    return base * half * half;
}
```

Assume that `exponent` is nonnegative. Write a recurrence before giving the bound.

**Point to consider:** Distinguish the numeric value of `exponent` from the number of bits needed to represent it.

### Function D — Rectangular matrix multiplication

```java
public static int[][] multiply(int[][] first, int[][] second) {
    int rows = first.length;
    int shared = first[0].length;
    int columns = second[0].length;
    int[][] product = new int[rows][columns];

    for (int row = 0; row < rows; row++) {
        for (int column = 0; column < columns; column++) {
            for (int k = 0; k < shared; k++) {
                product[row][column]
                        += first[row][k] * second[k][column];
            }
        }
    }

    return product;
}
```

Assume that `first` has dimensions $r\times s$ and `second` has dimensions $s\times c$. Count the multiplications performed by the innermost statement.

**Point to consider:** The returned matrix occupies memory, but it may not count as auxiliary space under the usual definition.

---

## Exercise 2 — Range-Sum Queries

A **range-sum query** asks for the sum of the array elements between two indices, inclusive.

For example, given:

```text
values = [4, 2, 7, 1, 6]
```

the query `[1, 3]` returns $2+7+1=10$.

The starter file implements two solutions:

- `answerDirectly` traverses the requested range for every query.
- `answerWithPrefixSums` first builds a prefix-sum array and then answers each query by subtraction.

### Part A — Single-run experiment

1. Read the provided `RangeSumExperiment.java` file.
2. Implement `timeDirectOnce` and `timePrefixOnce`. Each method must execute the corresponding algorithm and return its execution time in nanoseconds.
3. Do not include input generation or printing in the measured interval.
4. Compile and execute the complete program.
5. Copy the single-run timing table into `Module02Answers.pdf`.
6. Briefly describe what you observe as the input size grows.

### Part B — Repeated experiment

A single timing can be distorted by Java's just-in-time compilation, operating-system scheduling, garbage collection, and other activity on the computer. Repeating a test reduces the influence of an unusually fast or slow run.

1. Implement `averageDirectTime` and `averagePrefixTime`.
2. Each method must run the corresponding algorithm the requested number of times and return the mean execution time in nanoseconds.
3. Run the program again and copy the repeated-run timing table into your PDF.
4. Compare the repeated results with the single-run results. Did repetition make the trend clearer or more stable?

### Part C — Algorithmic analysis

Let:

- $n$ be the number of values in the array;
- $q$ be the number of queries.

Answer the following questions:

1. Determine the worst-case time complexity of `answerDirectly` in terms of $n$ and $q$.
2. Determine the time needed by `answerWithPrefixSums` to:
   - construct the prefix-sum array;
   - answer one query;
   - answer all $q$ queries.
3. Give the overall growth class of `answerWithPrefixSums`.
4. Which algorithm is asymptotically faster when both $n$ and $q$ grow?
5. Do the theoretical results correlate with your empirical findings? If your inputs did not show a clear difference, predict what should happen for larger inputs and justify your prediction.
6. Determine the auxiliary-space complexity of both algorithms. Do not count the returned answer array, because both algorithms must return it.
7. Explain the time–space tradeoff between the two solutions.
8. If there were only one short query, would constructing prefix sums necessarily be worthwhile? Explain how your answer changes when the same array receives many queries.

