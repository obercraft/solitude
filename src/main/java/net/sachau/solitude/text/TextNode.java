package net.sachau.solitude.text;

import javafx.scene.text.TextFlow;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TextNode {

    List<StyledText> parts = new LinkedList<>();
    boolean withWhitespaces = true;
    Map<String, String> styles = new HashMap<>();

    public TextNode() {
        this.parts = new LinkedList<>();
    }

    public TextNode(boolean withWhitespaces) {
        this.withWhitespaces = withWhitespaces;
    }

    public static TextNode builder() {
           return new TextNode();
    }

    public static TextNode builder(boolean withWhitespaces) {
        return new TextNode(withWhitespaces);
    }

    public TextNode ws() {
        parts.add(new StyledText(" "));
        return this;
    }

    public TextNode add(String string) {
        if (StringUtils.isEmpty(string)) {
            return this;
        }
        String[] args = string.split("\\s+", -1);
        for (String arg : args) {
            if ("$EXHAUSTED$".equals(arg)) {
                    symbol(Symbol.FA_HAND_HOLDING.getText());
            } else {
                parts.add(new StyledText(arg + (withWhitespaces ? " " : ""), new HashMap<>(styles)));
            }
        }
        return this;

    }

    public TextNode symbol(String symbol) {
        styleOffAllFonts();
        styleOn("font", "symbol");
        if (!styles.containsKey("size")) {
            styleOn("size", "12");
        }
        parts.add(new StyledText(symbol, new HashMap<>(styles)));
        styleOff("font");
        styleOff("size");
        return this;
    }

    private TextNode styleOffAllFonts() {
        for (Map.Entry<String, String> style : styles.entrySet()) {
            if (style.getKey().equalsIgnoreCase("font")) {
                styles.remove(style.getKey());
            }
        }

        return this;
    }

    public TextFlow write() {
        TextFlow textFlow = new TextFlow();
        for (StyledText styledText : parts) {
            textFlow.getChildren().add(styledText.get());
        }
        return textFlow;
    }

    public TextFlow writeln() {
        nl();
        return write();
    }

    public TextNode styleOn(String style, String value) {
        styles.put(style, value);
        return this;
    }

    public TextNode styleOff(String style) {
        styles.remove(style);
        return this;
    }

    public void reset() {
        for (String key : styles.keySet()) {
            styles.remove(key);
        }
    }

    public TextNode nl() {
        return nl(false);
    }

    public TextNode nl(boolean force) {
        try {
            if (force || parts.size() == 0 || !parts.get(parts.size() - 1).getText().endsWith("\n")) {
                parts.add(new StyledText("\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

}
