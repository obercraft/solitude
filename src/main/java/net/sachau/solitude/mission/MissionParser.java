package net.sachau.solitude.mission;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MissionParser {

    public static MissionMapParseResult parseMission(String id) throws IOException {
        InputStream mapStream = MissionParser.class.getResourceAsStream("/missions/" + id + ".map");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mapStream));

        String line;
        List<String> lines = new ArrayList<>();
        int height = 0;
        int width = 0;
        while((line = bufferedReader.readLine()) != null) {
            if (line.trim().startsWith("//")) {
                continue;
            } else if (StringUtils.isEmpty(line.trim())) {
                continue;
            }
            lines.add(line.replaceAll("[\r\n]", ""));
            height ++;
            width = Math.max(width, line.length());
        }

        return new MissionMapParseResult(height, width, lines);



    }
}
