package net.sachau.solitude;

import net.sachau.solitude.engine.Component;

import java.util.Locale;
import java.util.ResourceBundle;

public class Message {

    private ResourceBundle bundle;

    private static Message message;

    private Message() {

        bundle
                = ResourceBundle.getBundle("messages", Locale.getDefault());
        String message = bundle.getString("label");

    }

    public static Message getInstance() {
        if (message == null) {
            message =  new Message();
        }
        return message;
    }
}
