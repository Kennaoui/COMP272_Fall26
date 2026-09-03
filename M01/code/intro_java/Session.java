package basics.demo;

import java.util.Objects;

/** A conference session with controlled access to its state. */
public class Session {
    private final String title;
    private final String speaker;
    private int registeredSeats;

    public Session(String title, String speaker) {
        this.title = title;
        this.speaker = speaker;
        this.registeredSeats = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public int getRegisteredSeats() {
        return registeredSeats;
    }

    public void registerAttendee() {
        registeredSeats++;
    }

    private String displayLabel() {
        return title + " — " + speaker;
    }

    @Override
    public String toString() {
        return displayLabel() + " (registered: " + registeredSeats + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Session)) {
            return false;
        }
        Session session = (Session) other;
        return title.equals(session.title) && speaker.equals(session.speaker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, speaker);
    }
}
