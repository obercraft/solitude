package net.sachau.solitude.model;

import net.sachau.solitude.engine.Id;

public class Door extends Space{

    private long id = Id.get();
    private boolean vertical;

    public Door(int y, int x, boolean closed) {
        super(y, x);
        this.setClosed(closed);
    }

    public long getId() {
        return id;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
}
