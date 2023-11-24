package com.solvd.reader.execute;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import java.io.File;
import java.io.IOException;

public class Main {
    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
    }
    private final static Logger LOGGER = (Logger) LogManager.getLogger(Main.class);

    public static String readFile(File file, String charsetName) throws IOException {
        return FileUtils.readFileToString(new File("text.txt"), "UTF-8");
    }
    public static void main(String[] args)
    {
        String file = "text.txt";
        String content = null;
        try {
            content = readFile(new File(file), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("\"File not exist or unreadable\"");
        }
        System.out.println(content);
    }
}
