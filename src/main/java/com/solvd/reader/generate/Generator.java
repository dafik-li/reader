package com.solvd.reader.generate;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import java.io.File;
import java.io.IOException;

public class Generator {
    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
    }
    private final static Logger LOGGER = (Logger) LogManager.getLogger(Generator.class);
    public static String readFile() {
        try {
            return FileUtils.readFileToString(new File("/src/main/java/com/solvd/reader/data/text.txt"), "UTF-8");
        } catch (IOException e) {
            LOGGER.error("\"File not exist or unreadable\"");
        }
        return null;
    }
}
