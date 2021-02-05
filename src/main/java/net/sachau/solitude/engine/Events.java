package net.sachau.solitude.engine;

import java.util.Observable;

public class Events extends Observable {

    public Events() {
        super();
    }

    public void send(Event event) {
        setChanged();
        notifyObservers(event);
    }

    public void send(EventContainer eventContainer) {
        setChanged();
        notifyObservers(eventContainer);
    }

    public static Event getType(Object object) {
        if (object instanceof Event) {
            return (Event) object;
        } else if (object instanceof EventContainer) {
            return ((EventContainer) object).getEvent();
        } else {
            return null;
        }
    }

    public static <T> T getData(Class<T> type, Object object) {
        if (object instanceof EventContainer) {
            return (T) ((EventContainer ) object).getData();
        } else {
            return null;
        }
    }

    public static Object getData(Object object) {
        if (object instanceof EventContainer) {
            return ((EventContainer ) object).getData();
        } else {
            return null;
        }
    }

}
