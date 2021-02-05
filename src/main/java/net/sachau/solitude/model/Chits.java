package net.sachau.solitude.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chits<CHIT> {

    private List<CHIT> chits;

    public Chits() {
        super();
        chits = new ArrayList<>();

    }

    public void add(CHIT chit) {
        this.chits.add(chit);
        Collections.shuffle(chits);
    }

    public CHIT draw() {
        if (chits.size() > 0) {
            return chits.remove(0);
        } else {
            return null;
        }
    }

    public boolean hasChits() {
        return chits.size() > 0;
    }
}
