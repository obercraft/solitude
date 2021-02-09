package net.sachau.solitude.missions;

import java.util.List;

public class MissionMapParseResult {

    private int height, width;
    private List<String> lines;

    public MissionMapParseResult(int height, int width, List<String> lines) {
        this.height = height;
        this.width = width;
        this.lines = lines;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
