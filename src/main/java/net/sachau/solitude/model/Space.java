package net.sachau.solitude.model;

import net.sachau.solitude.card.EventCard;

public abstract class Space {

    private int y,x;
    private boolean closed;
    private boolean locked;
    private boolean blank = false;
    private boolean discovered;

    public EventCard eventCard;

    public Space(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public EventCard getEventCard() {
        return eventCard;
    }

    public void setEventCard(EventCard eventCard) {
        this.eventCard = eventCard;
    }


    @Override
    public String toString() {
        return "Space{" +
                "y=" + y +
                ", x=" + x +
                ", closed=" + closed +
                ", locked=" + locked +
                ", blank=" + blank +
                ", discovered=" + discovered +
                ", eventCard=" + eventCard +
                '}';
    }
}
