package net.sachau.solitude.text;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TextParser {

    public static Map<String, TextNode> parse() throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                if (systemId != null && systemId.contains("texts.dtd")) {
                    InputSource source = new InputSource(this.getClass()
                            .getResourceAsStream("/texts/texts.dtd"));
                    return source;
                }
                return null;
            }
        });

        Document document = builder.parse(new InputSource(TextParser.class
                .getResourceAsStream("/texts/texts.xml")));

        Element root = document.getDocumentElement();

        Map<String, TextNode> result = new HashMap<>();
        for (int i = 0; i< root.getChildNodes().getLength(); i++) {
            TextNode textNode = new TextNode();
            StringBuilder name = new StringBuilder();
            parse(root.getChildNodes().item(i), name, textNode);
            if (!StringUtils.isEmpty(name.toString())) {
                result.put(name.toString()
                        .trim(), textNode);
            } else {
                // throw new RuntimeException("textNode without name found!");
            }
        }
        return result;

    }

    private static void parse(Node node, StringBuilder name, TextNode textBuilder) {
        if (node == null) {
            return;
        }

        if (node.getAttributes() == null) {
            return;
        }

        Map<String, String> attributes = XmlUtils.getAttributes(node);
        if (attributes.get("id") != null) {
            name.append(attributes.get("id"));
        }

        for (int i = 0; i < node.getChildNodes()
                .getLength(); i++) {
            Node child = node.getChildNodes()
                    .item(i);
            String nodeName = child.getNodeName();
            if (nodeName.matches("h\\d+")) {
                textBuilder.styleOn("font", "standard");
                int size = 12 * (4 - NumberUtils.toInt(nodeName.replace("h", ""), 1));
                textBuilder.styleOn("size", "" + size);
                parse(child, name, textBuilder);
                textBuilder.nl()
                        .styleOff("font")
                        .styleOff("size");


            } else if ("p".equals(nodeName)) {
                textBuilder.nl();
                parse(child, name, textBuilder);
                textBuilder.nl();
            } else if ("color".equalsIgnoreCase(nodeName)) {
                String colorName = XmlUtils.getAttributes(child)
                        .get("name");
                textBuilder.styleOn("color", colorName);
                parse(child, name, textBuilder);
                textBuilder.styleOff("color");
            } else {
                textBuilder.add(child.getTextContent()
                        .trim());
                parse(child, name, textBuilder);
            }

        }
    }

}