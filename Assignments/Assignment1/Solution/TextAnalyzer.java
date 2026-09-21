import java.util.ArrayDeque;
import java.util.Deque;

/**
 * TextAnalyzer: a small class of static helper methods that check simple
 * properties of a String, some of them using a stack.
 *
 * A stack only allows access at one end (the "top"): push adds, pop
 * removes and returns the most recently pushed item. This class uses
 * java.util.ArrayDeque as the stack, through the Deque interface --
 * NOT java.util.Stack. java.util.Stack is an older class kept in Java
 * for backward compatibility: it is synchronized (overhead most programs
 * do not need), and because it is also a List it allows indexed access,
 * which breaks the "top only" idea a stack is supposed to guarantee.
 * ArrayDeque is the class Java's own documentation recommends instead.
 */
public class TextAnalyzer {

    /**
     * Returns true if input reads the same forwards and backwards,
     * ignoring letter case and ignoring spaces.
     *
     * Examples:
     *   isPalindrome("Race Car")            -> true
     *   isPalindrome("Never odd or even")   -> true
     *   isPalindrome("ab ca")               -> false
     *
     * Approach: normalize the string (lower case, spaces removed), push
     * every character onto the stack, then pop characters back off one
     * at a time -- since a stack is LIFO, they come off in reverse order
     * -- comparing each popped character to the matching character of
     * the normalized string, read from the front.
     */
    public static boolean isPalindrome(String input) {
        String normalized = input.toLowerCase().replaceAll("\\s+", "");
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < normalized.length(); i++) {
            stack.push(normalized.charAt(i));
        }

        for (int i = 0; i < normalized.length(); i++) {
            char popped = stack.pop();
            if (popped != normalized.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
