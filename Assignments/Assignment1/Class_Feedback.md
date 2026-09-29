# Assignment 1 — Class Feedback

Overall, this was a strong first assignment. Most of you clearly got comfortable with linked nodes, stacks, and queues — the three building blocks the rest of this course leans on constantly. A few patterns showed up across enough submissions that they're worth talking about as a class, so here's the rundown: what went well, where the trouble came from, and then a walk through each part with the reasoning laid out.

## What went well

- **Pointer manipulation.** Insertion and removal at an arbitrary index, and the full in-place reversal, are genuinely the trickiest mechanical skill in this assignment, and most of you had it solid: find the node before your target, relink around it, don't lose track of the node you're about to overwrite. That's the pattern you'll reuse for the rest of the semester.
- **The palindrome check.** Almost everyone landed on the right insight — a stack reverses whatever you feed it, so pushing a string and popping it back hands you the string backwards for free. That's the kind of "let the data structure do the work" thinking this course is trying to build.
- **Design reasoning.** Several of the written answers — especially on the trickiest question (more below) — showed real command of *why* an operation costs what it costs, not just a memorized Big-O label.

## Where the struggles came from

- **Not compiling before submitting.** A couple of submissions had completely correct logic undone by a single typo — `=` instead of `==`, a variable spelled with the wrong capitalization, a missing semicolon. If the code doesn't compile, nothing runs, and no logic gets credit no matter how right it is. Running `Main.java` yourself before you submit costs thirty seconds and catches all of this.
- **Reading the whole contract, not just the happy path.** A method's doc comment often lists more than one responsibility — return a value, *and* update a counter, *and* throw a specific exception in an edge case. It was common to nail the main return value and quietly miss the other requirements. Treat every sentence in the TODO comment as its own checklist item.
- **Skipping the justification.** Part 4 asks for reasoning, not just a number, and a few submissions left the answer correct but the justification blank. The number alone was never going to earn full credit — the reasoning is the actual point of the exercise.

## Part 1 — building the list

**`add(int index, T item)`** and **`remove(int index)`** both come down to the same move: walk from `head` to the node sitting *just before* your target index, then relink `next` pointers around it (past-the-end and index-0 are just edge cases of the same idea, not separate problems). **`reverseInPlace()`** wants a single pass with three references — the node you just left, the node you're on, and the node you're about to visit — so that you can flip each `next` pointer without ever losing the rest of the list. If you find yourself reaching for a second list or an array to help you reverse, that's a sign to step back: the whole point of the exercise is that the existing nodes already hold everything you need.

## Part 2 — the palindrome check

Push every character of the normalized string onto the stack, then pop them back off one at a time, comparing each popped character against the string read from the front. Since a stack is LIFO, popping naturally hands you the string in reverse — so a mismatch anywhere means it isn't a palindrome, and making it all the way through means it is.

## Part 3 — the ticket queue

**`serveNext()`** has three separate jobs, not one: check whether the line is empty and throw if so, remove and return the front customer, *and* increment the served count. **`isCustomerWaiting()`** just needs a read-only pass over the queue — Java lets you iterate a `Queue` directly without disturbing it, so there's no need to drain and rebuild it.

## Part 4 — the design questions, explained

**1. `addWithoutTailCost()` → O(n).** Without a tail reference, every call to `add(item)` starts at `head` and walks the whole chain to find the last node before it can attach anything. On a list of n elements, that's n steps just to find where to attach the next one.

**2. `addWithTailCost()` → O(1).** Once the list keeps a `tail` reference that's always kept correct, there's nothing to walk — you already have a direct handle on the last node, so attaching a new one and moving `tail` forward takes a fixed number of steps no matter how big the list is. This is exactly why real linked-list implementations keep a tail pointer.

**3. `rideShareScenario()` → this one was deliberately not clean-cut.** The literal matching rule — first-come, first-served — is FIFO, which is a queue's entire reason for existing, so "a queue" is a defensible answer. But look at what the scenario adds: a rider can cancel from *anywhere* in the line, not just the front. A plain queue, like the one you built in Part 3, only ever touches the two ends — it has no way to reach into the middle, let alone remove something from there efficiently. Once "remove from the middle" becomes a real requirement, you've quietly outgrown a pure queue's contract, and you need indexed access — the exact thing `SequenceList` was built for back in Part 1. Both answers earned credit here, as long as the justification showed you'd actually worked through *why*: that a plain ArrayDeque-based queue gives up its O(1) end-only guarantee the moment "cancel from the middle" enters the requirements. The real lesson isn't "memorize which ADT wins" — it's noticing the moment a system's requirements have outgrown the data structure that looked obvious at first glance.

Nice work overall — keep this pace up for the following assignments.
