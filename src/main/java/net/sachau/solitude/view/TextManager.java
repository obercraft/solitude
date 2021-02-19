package net.sachau.solitude.view;

import net.sachau.solitude.engine.View;
import net.sachau.solitude.text.TextNode;
import net.sachau.solitude.text.TextParser;

import java.util.Map;

@View
public class TextManager {


    private final Map<String, TextNode> texts;

    public TextManager() throws Exception {
       texts = TextParser.parse();
    }

    public TextNode getTextNode(String name) {
        return texts.get(name);
    }
}
