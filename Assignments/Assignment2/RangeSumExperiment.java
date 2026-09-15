import java.util.Arrays;

/**
 * Compares direct range summation with range queries based on prefix sums.
 *
 * Complete the four timing methods marked TODO. Do not change the two
 * range-query algorithms or the input-generation methods.
 */
public class RangeSumExperiment {

    /* Prevents a computed result from being completely ignored during timing. */
    private static volatile long resultSink;

    /**
     * Answers every query by adding the values in the requested range.
     * A query contains {left, right}, and both endpoints are inclusive.
     */
    public static long[] answerDirectly(int[] values, int[][] queries) {
        long[] answers = new long[queries.length];

        for (int query = 0; query < queries.length; query++) {
            int left = queries[query][0];
            int right = queries[query][1];
            long sum = 0;

            for (int index = left; index <= right; index++) {
                sum += values[index];
            }

            answers[query] = sum;
        }

        return answers;
    }

    /**
     * Constructs prefix sums and uses them to answer every query.
     */
    public static long[] answerWithPrefixSums(
            int[] values, int[][] queries) {

        long[] prefix = new long[values.length + 1];

        for (int index = 0; index < values.length; index++) {
            prefix[index + 1] = prefix[index] + values[index];
        }

        long[] answers = new long[queries.length];

        for (int query = 0; query < queries.length; query++) {
            int left = queries[query][0];
            int right = queries[query][1];
            answers[query] = prefix[right + 1] - prefix[left];
        }

        return answers;
    }

    /**
     * Measures one execution of answerDirectly.
     *
     * TODO: Execute the method, save one result in resultSink, and return
     * the elapsed time in nanoseconds. Only the algorithm call should be
     * inside the measured interval.
     */
    public static long timeDirectOnce(int[] values, int[][] queries) {
        return 0;
    }

    /**
     * Measures one execution of answerWithPrefixSums.
     *
     * TODO: Execute the method, save one result in resultSink, and return
     * the elapsed time in nanoseconds. Only the algorithm call should be
     * inside the measured interval.
     */
    public static long timePrefixOnce(int[] values, int[][] queries) {
        return 0;
    }

    /**
     * Returns the mean execution time of answerDirectly.
     *
     * TODO: Run the algorithm repetitions times. Save one result from every
     * execution in resultSink so that the computed answer is used.
     */
    public static long averageDirectTime(
            int[] values, int[][] queries, int repetitions) {
        return 0;
    }

    /**
     * Returns the mean execution time of answerWithPrefixSums.
     *
     * TODO: Run the algorithm repetitions times. Save one result from every
     * execution in resultSink so that the computed answer is used.
     */
    public static long averagePrefixTime(
            int[] values, int[][] queries, int repetitions) {
        return 0;
    }

    /**
     * Creates deterministic values in the range 1 through 100.
     */
    public static int[] createValues(int size) {
        int[] values = new int[size];

        for (int index = 0; index < size; index++) {
            values[index] = (index * 37 + 11) % 100 + 1;
        }

        return values;
    }

    /**
     * Creates count identical full-range queries [0, size - 1].
     * These inputs expose the worst-case work of direct summation.
     */
    public static int[][] createFullRangeQueries(int size, int count) {
        int[][] queries = new int[count][2];

        for (int query = 0; query < count; query++) {
            queries[query][0] = 0;
            queries[query][1] = size - 1;
        }

        return queries;
    }

    /**
     * Checks both algorithms on small, well-defined examples.
     */
    public static void runCorrectnessTests() {
        int[] values = {4, 2, 7, 1, 6};
        int[][] queries = {
            {1, 3},
            {0, 4},
            {2, 2},
            {3, 4}
        };
        long[] expected = {10, 20, 7, 7};

        long[] direct = answerDirectly(values, queries);
        long[] prefix = answerWithPrefixSums(values, queries);

        System.out.println("Correctness test");
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Direct:   " + Arrays.toString(direct));
        System.out.println("Prefix:   " + Arrays.toString(prefix));

        if (!Arrays.equals(expected, direct)
                || !Arrays.equals(expected, prefix)) {
            throw new AssertionError("A range-sum algorithm is incorrect.");
        }

        System.out.println("All correctness tests passed.\n");
    }

    public static void main(String[] args) {
        runCorrectnessTests();

        /* For every experiment, q = n and every query spans n elements. */
        int[] sizes = {500, 1_000, 2_000, 4_000, 8_000};
        int repetitions = 5;

        System.out.println("Single-run experiment (nanoseconds)");
        System.out.printf(
                "%-10s %-10s %-18s %-18s%n",
                "n", "q", "Direct", "Prefix");

        for (int size : sizes) {
            int[] values = createValues(size);
            int[][] queries = createFullRangeQueries(size, size);

            long directTime = timeDirectOnce(values, queries);
            long prefixTime = timePrefixOnce(values, queries);

            System.out.printf(
                    "%-10d %-10d %-18d %-18d%n",
                    size, queries.length, directTime, prefixTime);
        }

        System.out.println();
        System.out.println(
                "Repeated experiment: mean of "
                        + repetitions + " runs (nanoseconds)");
        System.out.printf(
                "%-10s %-10s %-18s %-18s%n",
                "n", "q", "Direct", "Prefix");

        for (int size : sizes) {
            int[] values = createValues(size);
            int[][] queries = createFullRangeQueries(size, size);

            long directTime = averageDirectTime(
                    values, queries, repetitions);
            long prefixTime = averagePrefixTime(
                    values, queries, repetitions);

            System.out.printf(
                    "%-10d %-10d %-18d %-18d%n",
                    size, queries.length, directTime, prefixTime);
        }
    }
}
