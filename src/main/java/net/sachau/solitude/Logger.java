package net.sachau.solitude;

public class Logger {


    private static Logger logger = new Logger();

    private Logger() {
    }


    public static void debug(String text) {
        logger.log(text);
    }

    private void log(String text) {
        System.out.println(text);
    }
}
