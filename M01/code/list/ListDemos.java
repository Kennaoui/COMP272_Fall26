import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * ListDemos: short runnable tours of the Module 1 structures. Each
 * demoXxx() method is self-contained and prints one labelled block.
 *
 *   javac SimpleList.java LinkedSimpleList.java IterableLinkedSimpleList.java \
 *         ArraySimpleList.java ListDemos.java
 *   java ListDemos
 */
public class ListDemos {

    public static void main(String[] args) {
        // demoLinkedSimpleList();
        // demoArraySimpleList();
        // demoJavaArrayList();
        // demoIterableLinkedSimpleList();
        demoProgramToTheInterface();
        // demoStackWithArrayDeque();
        // demoQueueWithArrayDeque();
    }

    private static void heading(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    // -- 1. LinkedSimpleList --------------------------------------------

    private static void demoLinkedSimpleList() {
        heading("LinkedSimpleList (singly linked, head + size)");

        LinkedSimpleList<String> list = new LinkedSimpleList<>();
        list.add("Amina");
        list.add("Ben");
        list.add("Cy");
        System.out.println("after 3 appends : " + list);
        System.out.println("get(1)          : " + list.get(1));
        System.out.println("size / isEmpty  : " + list.size() + " / " + list.isEmpty());

        try {
            list.get(9);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("get(9)          : rejected, " + e.getMessage());
        }
    }

    // -- 2. ArraySimpleList --------------------------------------------
    // Resizing-array implementation of the same ADT. Append grows the
    // backing array when full; add/remove in the middle shift a block.
    private static void demoArraySimpleList() {
        heading("ArraySimpleList (resizing array)");

        ArraySimpleList<Integer> nums = new ArraySimpleList<>();
        for (int i = 1; i <= 10; i++) {
            nums.add(i);                // append -- O(1) amortized, grows when full
        }
        System.out.println("after 10 appends : " + nums);

        nums.add(0, 0);                 // insert at front -- shifts the block right
        System.out.println("add(0, 0)        : " + nums);

        int removed = nums.remove(5);   // remove from the middle -- shifts left
        System.out.println("remove(5) -> " + removed + "   : " + nums);

        System.out.print("read back by index :");
        for (int i = 0; i < nums.size(); i++) {
            System.out.print(" " + nums.get(i));
        }
        System.out.println();
    }

    // -- 3. java.util.ArrayList ---------------------------------------
    // The standard library's resizing-array list: generic and fully
    // featured. Client code is written against List, not ArrayList.
    private static void demoJavaArrayList() {
        heading("java.util.ArrayList (the standard library)");

        List<String> names = new ArrayList<>();
        names.add("Amina");
        names.add("Cy");
        names.add(1, "Ben");           // O(n) insert -- shifts the rest right
        names.set(2, "Cyrus");         // O(1) replace
        System.out.println("list            : " + names);
        System.out.println("get(0)          : " + names.get(0));
        System.out.println("indexOf(Ben)    : " + names.indexOf("Ben"));
        System.out.println("contains(Cyrus) : " + names.contains("Cyrus"));
        names.remove("Ben");           // remove first match -- O(n)
        System.out.println("after remove    : " + names + "  size " + names.size());

        System.out.print("for-each        :");
        for (String n : names) {
            System.out.print(" " + n);
        }
        System.out.println();
    }

    // -- 4. IterableLinkedSimpleList --------------------------------
    // The same linked list, plus Iterable<T>: for-each, an explicit
    // iterator, independent cursors, and next() past the end.
    private static void demoIterableLinkedSimpleList() {
        heading("IterableLinkedSimpleList (linked + Iterator)");

        IterableLinkedSimpleList<String> route = new IterableLinkedSimpleList<>();
        route.add("Rogers Park");
        route.add("Loyola");
        route.add("Granville");

        System.out.print("for-each        :");
        for (String stop : route) {
            System.out.print(" [" + stop + "]");
        }
        System.out.println();

        System.out.println("toString        : " + route + "  (built with its own iterator)");

        System.out.println("what for-each expands to:");
        Iterator<String> it = route.iterator();
        while (it.hasNext()) {
            System.out.println("  next() -> " + it.next());
        }

        Iterator<String> a = route.iterator();
        Iterator<String> b = route.iterator();
        a.next();                       // advance only a
        System.out.println("independent cursors : a=" + a.next() + ", b=" + b.next());

        try {
            it.next();                  // it is already exhausted
        } catch (NoSuchElementException e) {
            System.out.println("next() past the end : NoSuchElementException");
        }
    }

    // -- 5. Program to the interface -------------------------------
    // fill() names no concrete class -- it works on any SimpleList<T>.
    private static SimpleList<String> fill(SimpleList<String> list) {
        list.add("x");
        list.add("y");
        list.add("z");
        return list;
    }

    
    private static void demoProgramToTheInterface() {
        heading("One client, three representations (SimpleList<T>)");

        System.out.println("ArraySimpleList         -> " + fill(new ArraySimpleList<>()));
        System.out.println("LinkedSimpleList        -> " + fill(new LinkedSimpleList<>()));
        System.out.println("IterableLinkedSimpleList -> " + fill(new IterableLinkedSimpleList<>()));
    }



    // -- 6. Stack via ArrayDeque ---------------------------------
    // LIFO, all O(1). Use ArrayDeque through Deque -- not
    // java.util.Stack (legacy, synchronized, leaks indexed access).
    private static void demoStackWithArrayDeque() {
        heading("Stack via ArrayDeque (push / pop / peek)");

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("after push 1,2,3 : " + stack + "  (top on the left)");
        System.out.println("peek            : " + stack.peek());
        System.out.println("pop             : " + stack.pop());
        System.out.println("pop             : " + stack.pop());
        System.out.println("left            : " + stack + "  size " + stack.size());

        System.out.println("bracketsBalanced(\"a[b(c)d]\") : " + bracketsBalanced("a[b(c)d]"));
        System.out.println("bracketsBalanced(\"a[b(c]d)\") : " + bracketsBalanced("a[b(c]d)"));

        try {
            stack.push(null);           // ArrayDeque forbids null
        } catch (NullPointerException e) {
            System.out.println("push(null)      : rejected (ArrayDeque forbids null)");
        }
        try {
            new ArrayDeque<Integer>().pop();
        } catch (NoSuchElementException e) {
            System.out.println("pop() on empty  : NoSuchElementException");
        }
    }

    // A classic stack use, distinct from anything in the assignment:
    // every closer must match the most recent unmatched opener.
    private static boolean bracketsBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if ((c == ')' && open != '(')
                        || (c == ']' && open != '[')
                        || (c == '}' && open != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // -- 7. Queue via ArrayDeque -------------------------------
    // FIFO, all O(1). Use ArrayDeque through Queue. poll() and peek()
    // return null on an empty queue instead of throwing.
    private static void demoQueueWithArrayDeque() {
        heading("Queue via ArrayDeque (offer / poll / peek)");

        Queue<String> line = new ArrayDeque<>();
        line.offer("Ada");
        line.offer("Grace");
        line.offer("Linus");
        System.out.println("line            : " + line);
        System.out.println("peek (front)    : " + line.peek());

        System.out.print("iterate, no removal :");
        for (String person : line) {
            System.out.print(" " + person);
        }
        System.out.println("   (size still " + line.size() + ")");

        System.out.println("poll            : " + line.poll());
        System.out.println("poll            : " + line.poll());
        System.out.println("left            : " + line);

        line.poll();                    // drain the last one
        System.out.println("poll() on empty : " + line.poll() + "  (null, not an exception)");
    }
}
