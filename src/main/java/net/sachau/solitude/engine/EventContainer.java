package net.sachau.solitude.engine;

public class EventContainer {

    private Event event;
    private Object data;


    public EventContainer(Event event, Object data) {
        this.event = event;
        this.data = data;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
