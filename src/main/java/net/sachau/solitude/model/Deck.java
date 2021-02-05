package net.sachau.solitude.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<CARD extends Card> {

    private List<CARD> drawPile;
    private List<CARD> discardPile;

    public Deck() {
        super();
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    public void add(CARD card) {
        this.drawPile.add(card);
    }

    public void discard(CARD card) {
        this.discardPile.add(card);
        return;
    }

    public void shuffle() {
        for (CARD card : this.drawPile) {
            card.setVisible(false);
        }
        Collections.shuffle(this.drawPile);
    }

    public CARD draw() {
        if (this.drawPile.size() > 0) {
            CARD drawnCard = this.drawPile.remove(0);
            this.discardPile.add(drawnCard);
            return drawnCard;
        } else {
            this.drawPile.addAll(discardPile);
            this.discardPile.clear();
            shuffle();
            return this.draw();
        }

    }

    public List<CARD> getDiscardPile() {
        return this.discardPile;
    }

    public void addToDrawPile(CARD card) {
        this.drawPile.add(card);
    }


    public int getSize() {
        return drawPile.size() + discardPile.size();
    }

    public List<CARD> getDrawPile() {
        return drawPile;
    }
}
