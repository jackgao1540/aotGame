package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of events in the app. Uses the Singleton Design Pattern since only one log is necessary.
public class EventLog implements Iterable<Event> {
    private static EventLog theLog; // the yonely event log in the system.
    private Collection<Event> events;

    // EFFECTS: creates an event log (can only create from within the class (Singleton))
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // MODIFIES: this
    // EFFECTS: Creates/gets the single instance of an EventLog
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an Event to the EventLog
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the event log
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
