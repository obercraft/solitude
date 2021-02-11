package net.sachau.solitude.model;

import net.sachau.solitude.asset.Asset;

import java.util.HashSet;
import java.util.Set;

public class Room extends Space {

    private String name = "Room";
    private Set<Asset> assets = new HashSet<>();

    public Room(int y, int x) {
        super(y, x);
    }
    public Room(String name, int y, int x) {
        super(y, x);
        this.name = name;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public void addAsset(Asset asset, Space space) {
        asset.setSpace(space);
        this.assets.add(asset);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
