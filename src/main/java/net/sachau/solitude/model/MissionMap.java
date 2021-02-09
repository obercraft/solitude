package net.sachau.solitude.model;

import java.io.Serializable;

public class MissionMap implements Serializable {

    private int height = 5 * 2;
    private int width = 3 * 2;


    // height, with
    private Space[][] spaces;
    private int doorCount;

    public MissionMap() {
        spaces = new Space[height][width];
    }

    public MissionMap(int height, int width) {
        this.width = width;
        this.height = height;
        spaces = new Space[height+1][width+1];
    }

    public Space getSpace(int y, int x) {
        if (y < 0 || x < 0) {
            return null;
        } else {
            return spaces[y][x];
        }
    }

    public void addSpace(Space space, int y, int x) {
        spaces[y][x] = space;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Distance calculateDistance(int sourceY, int sourceX, int targetY, int targetX) {
        int roomCount = 0;
        boolean blocked = false;

        int b = Math.abs(targetY - sourceY);
        int a = Math.abs(targetX - sourceY);
        int spaceCount = (int) Math.sqrt(a*a + b*b);

        boolean diagonal = false;
        if (sourceX == targetX) {
            int start = Math.min(sourceY, targetY);
            int end = Math.max(sourceY, targetY);
            for (int y = start + 1; y <= end; y++) {
                Space space = spaces[y][sourceX];
                if (space != null) {
                    roomCount += space instanceof Room ? 1 : 0;
                    if (space.isLocked() || space.isClosed()) {
                        blocked = true;
                    }
                }
            }
        } else if (sourceY == targetY) {
            int start = Math.min(sourceX, targetX);
            int end = Math.max(sourceX, targetX);
            for (int x = start + 1; x <= end; x++) {
                Space space = spaces[sourceY][x];
                if (space != null) {
                    roomCount += space instanceof Room ? 1 : 0;
                    if (space.isLocked() || space.isClosed()) {
                        blocked = true;
                    }
                }
            }
        } else {
            diagonal = true;
        }

        return new Distance(roomCount, blocked, spaceCount, diagonal);
    }

}
