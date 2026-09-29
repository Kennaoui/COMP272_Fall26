/**
 * DesignAnalysis: answer key. Each method below answers a multiple-choice
 * question about cost or design, with the reasoning in the comment above
 * the return statement.
 */
public class DesignAnalysis {

    /**
     * Look back at add(T item) in LinkedSequenceList (Part 1) -- the
     * version given to you, not one of your TODOs. It has no tail
     * reference: every call walks all the way from head to the last
     * node before attaching the new node.
     *
     * What is the time complexity of calling add(item) once on a list
     * that already holds n elements?
     *
     *   1. O(1)
     *   2. O(log n)
     *   3. O(n)
     *   4. O(n^2)
     */
    public static int addWithoutTailCost() {
        // Justification:
        // Without a tail reference, add(item) has to start at head and
        // follow "next" links all the way to the last node before it can
        // attach the new one. On a list of n elements that is n steps, so
        // the cost grows in direct proportion to the size of the list.
        return 3; // O(n)
    }

    /**
     * Suppose LinkedSequenceList were changed to also keep a private
     * "tail" reference to its last node, kept correct on every add and
     * remove. What would add(item) become?
     *
     *   1. O(1)
     *   2. O(log n)
     *   3. O(n), same as before -- a tail reference would not help here
     *   4. O(n^2)
     */
    public static int addWithTailCost() {
        // Justification:
        // With a tail reference that is always kept correct, add(item) no
        // longer needs to walk anything -- it already has a direct
        // reference to the last node, so attaching the new node and
        // moving tail forward takes the same fixed number of steps no
        // matter how large the list already is.
        return 1; // O(1)
    }

    /**
     * A ride-share app matches drivers to riders strictly in the order
     * ride requests arrive. A rider may cancel their request while they
     * are still waiting to be matched, at any position in the waiting
     * line, not only at the front.
     *
     * Which contract from this assignment best fits the "waiting to be
     * matched" part of this system?
     *
     *   1. SequenceList -- requests are read and removed by index
     *   2. A stack -- the most recent request should be matched first
     *   3. A queue -- requests are matched in arrival order
     *   4. None of the above; no ordered collection is needed
     */
    public static int rideShareScenario() {
        // Justification: name the operation that dominates this
        // workload, and say what a plain ArrayDeque-based queue gives
        // up once "cancel a request from the middle" is added as a
        // requirement.
        //
        // Matching requests in arrival order is FIFO, which is exactly
        // what a queue is for. But once a rider can cancel from any
        // position, the system also needs to remove an element from the
        // middle of the waiting line -- something a plain ArrayDeque-based
        // queue cannot do efficiently, since it only ever touches its two
        // ends. Finding and removing a request in the middle of a queue
        // costs O(n) with no direct way to reach it, whereas SequenceList
        // was built exactly for indexed access and removal. Once "cancel
        // from the middle" is a real requirement, SequenceList is the
        // better fit. (A justification that instead argues for "a queue"
        // on the strength of the FIFO matching rule, while correctly
        // explaining this same O(1)-vs-O(n) tradeoff, was also accepted --
        // the reasoning matters more than the option number here.)
        return 1; // SequenceList
    }
}
