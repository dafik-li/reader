package com.solvd.reader.generate;

import com.solvd.reader.exception.CustomException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Generator {
    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
    }
    private final static Logger LOGGER = (Logger) LogManager.getLogger(Generator.class);
    private static String file;
    private final Scanner scanner;

    public Generator() {
        this.scanner = new Scanner(System.in);
    }

    public String readFile() {
        //LOGGER.info("\n" +  "Source text " + "\n" + file + "\n");
        try {
            file = FileUtils.readFileToString(new File("text.txt"), "UTF-8");
            return file;
        } catch (IOException e) {
            LOGGER.error("File not exist or unreadable");
        }
        return null;
    }
    public int countUniqueWords() {
        String lowercase = StringUtils.lowerCase(file);
        lowercase = RegExUtils.removePattern(lowercase, "[^а-яёa-z\\s]");
        List<String> uniqueWordsList = new ArrayList<>();
        for (String word : StringUtils.split(lowercase)) {
            if (!uniqueWordsList.contains(word)) {
                uniqueWordsList.add(word);
                try (PrintWriter writer = new PrintWriter("uniquewordslist.txt")) {
                    writer.println(uniqueWordsList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.error("File not exist or unwritable");
                }
            }
        }
        int numberOfTheUniqueWords = uniqueWordsList.size();
        LOGGER.info("File contains " + numberOfTheUniqueWords + " unique words" + "\n");
        return numberOfTheUniqueWords;
    }
    public void countLetters() {
        String removeChars = RegExUtils.removePattern(file, "[^A-Za-z]");
        int numberOfLetters = removeChars.length();
        String upperCase = StringUtils.upperCase(removeChars);
        List<Character> tempList = new ArrayList<>();
        for (char letter : upperCase.toCharArray()) {
            tempList.add(letter);
        }
        upperCase = StringUtils.joinWith(" ", tempList.toArray());
        try (PrintWriter writer = new PrintWriter("numberofletters.txt")) {
            writer.println(upperCase);
            writer.println(numberOfLetters);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("File not exist or unwritable");
        }
        LOGGER.info("\n" + "Source text - " + file + "\n" + "Number of letters - " + numberOfLetters + "\n" + "Result - " + upperCase);
    }
    public String checkEntered() {
        String enteredWord = scanner.nextLine();
        int lengthWord = enteredWord.length();
        try {
            LOGGER.info(enteredWord);
            if (lengthWord < 2) {
                throw new CustomException("Type a word with 1");
            }
            if (enteredWord.matches("[^a-zA-Z]+")) {
                throw new CustomException("Type a word with 2 ");
            }
            if (StringUtils.contains(enteredWord, " ")) {
                throw new CustomException("Type a word with 3");
            }
        } catch (CustomException e) {
            LOGGER.error(e.toString());
            return checkEntered();
        }
        return enteredWord;
    }

    public int findMatches() {
        LOGGER.info("Please type a desired word");
        String[] wordList = StringUtils.split(file, " ");
        int numberOfWords = 0;
        String enteredWord = checkEntered();
        for (String word : wordList) {
            if (StringUtils.equalsIgnoreCase(word, enteredWord)) {
                numberOfWords++;
            }
        }
        try (PrintWriter writer = new PrintWriter("matches.txt")) {
            writer.println(file);
            writer.println(enteredWord);
            writer.println(numberOfWords);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("File not exist or unwritable");
        }
        LOGGER.info("\n" + "Source text - " + file + "\n" + "Search by word - " + enteredWord + "\n" + "Find matches - " + numberOfWords);
        return numberOfWords;
    }
}

