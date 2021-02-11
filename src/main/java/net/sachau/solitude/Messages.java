package net.sachau.solitude;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private ResourceBundle bundle;

    private static Messages messages;

    private Messages() {

        bundle
                = ResourceBundle.getBundle("messages");
    }

    public static Messages getInstance() {
        if (messages == null) {
            messages =  new Messages();
        }
        return messages;
    }
    public static String get(String name) {
        return getInstance().getBundle().getString(name);
    }

    public static String get(String name, Object ...objects) {
        String pattern = getInstance().getBundle().getString(name);
        return MessageFormat.format(pattern, objects);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

}
