package net.sachau.solitude.model;

import java.util.HashSet;
import java.util.Set;

public class Room extends Space {

    private Set<Asset> assets = new HashSet<>();

    public Room(int y, int x) {
        super(y, x);
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public void addProperty(Asset property) {
        this.assets.add(property);
    }

    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }

}
