package net.sachau.solitude.gui;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Icons {

    public enum Name {

        PLAYER("/icons/PNG/noun_soldier_3562840.png"),
        LOCKER("/icons/PNG/20 Cargo - Storage Unit.png"),
        ELEVATOR("/icons/PNG/94 Teleport-02.png"),
        FIRE("/icons/PNG/noun_Fire_3723257.png"),
        FIRE_EXTINGUISHER("/icons/PNG/noun_Fire Extinguisher_1711612.png"),

        FIST("/icons/PNG/noun_Fist_3303334.png"),
        PISTOL("/icons/PNG/noun_Pistol_3107615.png"),
        ARMOR("/icons/PNG/noun_armor_710826.png"),

        FIRST_AID("/icons/PNG/noun_First Aid_31025.png"),
        TYPE1("/icons/PNG/noun_alien_1587501.png");

        private String fileName;

        Name(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }


    Map<Icons.Name, Image> cache = new HashMap<>();



    private static Icons icons;

    private Icons() {
    }

    public static Icons getInstance() {
        if (icons == null) {
            icons = new Icons();
        }
        return icons;
    }

    public static Image get(Icons.Name name) {
        if (getInstance().getCache().get(name) == null) {
            InputStream inputStream = Icons.class.getResourceAsStream(name.getFileName());
            Image image = new Image(inputStream);
            getInstance().getCache().put(name, image);
        }
        return getInstance().getCache().get(name);
    }

    public Map<Icons.Name, Image> getCache() {
        return cache;
    }

    public void setCache(Map<Icons.Name, Image> cache) {
        this.cache = cache;
    }
}
