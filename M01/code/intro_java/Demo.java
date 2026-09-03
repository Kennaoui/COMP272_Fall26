package intro_java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Demo {
    public static void main(String[] args) {



		// 1. Primitive values are copied.
		// int roomCapacity = 40;
        // roomCapacity = 45;
        // System.out.println("Primitive values: " + roomCapacity + ", " + copiedCapacity);

		// 2. Reference variables can refer to the same mutable object.
        // Session javaSession = new Session("Java Beyond Syntax", "Maya Chen");
        // Session sessionAlias = javaSession;
        // sessionAlias.registerAttendee();
        // System.out.println("Same object? " + (javaSession == sessionAlias));
        // System.out.println(javaSession);

        // 3. Strings are immutable; an operation produces another String.
        // String track = "software design";
        // String originalTrack = track;
        // track = track.toUpperCase();
        // System.out.println("Original: " + originalTrack + "; new: " + track);

        // 4. == compares references; equals compares meaningful content.
        // String literalA = "Java";
        // String literalB = "Java";
        // String constructed = new String("Java");
        // System.out.println("literalA == literalB: " + (literalA == literalB));
        // System.out.println("literalA == constructed: " + (literalA == constructed));
        // System.out.println("literalA.equals(constructed): " + literalA.equals(constructed));
        // System.out.println("constructed.intern() == literalA: "
        //         + (constructed.intern() == literalA));

        // 5. Create objects. Session overrides Object.toString().
        // Session dataSession = new Session("Data at City Scale", "Omar Reed");
        // Session securitySession = new Session("Usable Security", "Nina Patel");
        // System.out.println(dataSession);

        // 6. Fixed-size array.
        // Session[] schedule = {javaSession, dataSession, securitySession};
        // schedule[1] = securitySession;
        // System.out.println("Array: " + Arrays.toString(schedule));

        // 7. Dynamic array: fast indexed access and grows as needed.
        // ArrayList<Session> popularSessions = new ArrayList<>();
        // popularSessions.add(javaSession);
        // popularSessions.add(dataSession);
        // System.out.println("ArrayList: " + popularSessions);

        // 8. Linked list: nodes connected by references.
        // LinkedList<Session> waitingList = new LinkedList<>();
        // waitingList.add(securitySession);
        // waitingList.addFirst(javaSession);
        // System.out.println("LinkedList: " + waitingList);
    }
}
