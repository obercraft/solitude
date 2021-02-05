package net.sachau.solitude.model;

public class Distance {

    private int rooms;
    boolean blocked;
    boolean diagonal;
    private int spaces;

    public Distance() {
    }

    public Distance(int rooms, boolean blocked, int spaces, boolean diagonal) {
        this.rooms = rooms;
        this.blocked = blocked;
        this.diagonal = diagonal;
        this.spaces = spaces;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    public void setDiagonal(boolean diagonal) {
        this.diagonal = diagonal;
    }
}
