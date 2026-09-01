# COMP 272 — Assignment 1
### Building and Using a List, a Stack, and a Queue

**Student:** _\<Your Name\>_  **Section:** _\<Your Section\>_

## Before you start

This assignment has four parts. In Parts 1–3 you complete starter code —
methods marked `TODO` — and your work is checked automatically by
`Main.java`. Part 4 asks a few short design questions instead of code.

- Do all of your work in `LinkedSequenceList.java`, `TextAnalyzer.java`,
  `TicketQueue.java`, and `DesignAnalysis.java`. Do **not** modify
  `SequenceList.java` or `Main.java`.
- You may add private helper methods if that makes your solution
  cleaner, but do not change any method signature that is already given.
- Run `Main.java` to test your work. It prints an `ERROR` line for each
  failing check and a running score out of 70. Part 4 (30 points) is
  graded separately, from your written answers.
- Submit your completed files using the Assignments tab on Sakai.

**Total: 100 points** (70 auto-tested + 30 written).

---

## Part 1 — A Generic List (`SequenceList` / `LinkedSequenceList`) — 30 pts

`SequenceList.java` states the List ADT: `add`, `add(index, item)`,
`get`, `remove`, `size`, `isEmpty` — for *any* element type `T`, not just
one fixed type. `LinkedSequenceList.java` implements it using linked
nodes, and already gives you a working `add(item)` (append), `get`,
`size`, `isEmpty`, and `toString`.

Complete the three methods marked `TODO`:

1. **`add(int index, T item)`** (10 pts) — insert `item` so it lands at
   position `index`, shifting later elements over by one.
2. **`remove(int index)`** (10 pts) — remove and return the element at
   `index`, closing the gap.
3. **`reverseInPlace()`** (10 pts) — reverse the list by relinking the
   existing nodes only. No extra data structure, no new nodes.

---

## Part 2 — Using a Stack (`TextAnalyzer`) — 20 pts

Complete **`isPalindrome(String input)`** in `TextAnalyzer.java`
(20 pts): it must return `true` exactly when `input` reads the same
forwards and backwards, ignoring case and spaces. You are required to
use a `Deque<Character>` (via `ArrayDeque`) as a stack — see the
comments in the starter file for the exact approach.

---

## Part 3 — Using a Queue (`TicketQueue`) — 20 pts

`TicketQueue.java` models a first-come-first-served customer line,
built on `ArrayDeque` used as a `Queue`. Complete:

1. **`serveNext()`** (10 pts) — remove and return the front customer,
   counting how many have been served; throw `NoSuchElementException`
   if the line is empty.
2. **`isCustomerWaiting(String customerId)`** (10 pts) — check whether a
   customer is anywhere in the line, without removing anyone.

---

## Part 4 — Design Analysis (`DesignAnalysis.java`) — 30 pts, written

No automated tests here. For each of the three methods in
`DesignAnalysis.java`, replace the `-1` with the number of the correct
option and write a one- or two-sentence justification in the comment
directly above the `return` statement. All three questions are about
code or scenarios from this same assignment — look back at what you
built in Parts 1–3 before answering.

- `addWithoutTailCost()` (10 pts)
- `addWithTailCost()` (10 pts)
- `rideShareScenario()` (10 pts)
