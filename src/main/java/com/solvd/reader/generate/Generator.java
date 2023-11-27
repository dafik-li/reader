package com.solvd.reader.generate;

import com.solvd.reader.exception.ChooseMethodException;
import com.solvd.reader.exception.CustomException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Generator {
    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
    }
    private final static Logger LOGGER = (Logger) LogManager.getLogger(Generator.class);
    private String file;
    private final Scanner scanner;

    public Generator() {
        this.scanner = new Scanner(System.in);
    }


    public String chooseMethod() throws ChooseMethodException {
        LOGGER.info("Type a text or read a file" + "\n" + "1 - type text" + "\n" + "2 - read file");
        String menu = scanner.nextLine();
        switch (menu) {
            case "1": typeFile(); break;
            case "2": readFile(); break;
        }
        try {
            if (!menu.equals("1") && !menu.equals("2")) {
                throw new ChooseMethodException("Please choose a desire method");
            }
        } catch (ChooseMethodException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
            return chooseMethod();
        }
        return null;
    }
    public String typeFile() {
        LOGGER.info("Type your text bellow" + "\n" + "Press 'exit' to finish typing");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while (((line = reader.readLine()) != null) && (!line.equals("exit"))) {
                List<String> text = Arrays.asList(line.split("\\s"));
                try (PrintWriter writer = new PrintWriter("text.txt")) {
                    writer.println(text);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.error("File not exist or unwritable");
                }
                readFile();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return file;
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
        lowercase = RegExUtils.removePattern(lowercase, "[^a-z\\s]");
        List<String> uniqueWordsList = new ArrayList<>();
        for (String word : StringUtils.split(lowercase)) {
            if (!uniqueWordsList.contains(word)) {
                uniqueWordsList.add(word);
                int numberOfTheUniqueWords = uniqueWordsList.size();
                try (PrintWriter writer = new PrintWriter("uniquewordslist.txt")) {
                    writer.println("Source text - " + "\n"  + file);
                    writer.println("Unique words: " + uniqueWordsList);
                    writer.println("File contains " + numberOfTheUniqueWords + " unique words");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.error("File not exist or unwritable");
                }
            }
        }
        int numberOfTheUniqueWords = uniqueWordsList.size();
        LOGGER.info("\n" + "Source text - " + "\n" + file + "\n" + "File contains " + numberOfTheUniqueWords + " unique words" + "\n");
        return numberOfTheUniqueWords;
    }
    public void countLetters() {
        String removeChars = RegExUtils.removePattern(file, "[^a-zA-Z]");
        int numberOfLetters = removeChars.length();
        String upperCase = StringUtils.upperCase(removeChars);
        List<Character> tempList = new ArrayList<>();
        for (char letter : upperCase.toCharArray()) {
            tempList.add(letter);
        }
        upperCase = StringUtils.joinWith(" ", tempList.toArray());
        try (PrintWriter writer = new PrintWriter("numberofletters.txt")) {
            writer.println("Source text - " + "\n"  + file);
            writer.println("Result - " + upperCase);
            writer.println("Number of letters - " + numberOfLetters);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("File not exist or unwritable");
        }
        LOGGER.info("\n" + "Source text - " + "\n" + file + "\n" + "Number of letters - " + numberOfLetters + "\n" + "Result - " + upperCase);
    }
    public String checkEntered() {
        String enteredWord = scanner.nextLine();
        int lengthWord = enteredWord.length();
        try {
            LOGGER.info(enteredWord);
            if (lengthWord < 2) {
                throw new CustomException("Type a word with two or more letters");
            }
            if (enteredWord.matches("[^a-zA-Z]+")) {
                throw new CustomException("Type a word!");
            }
            if (StringUtils.contains(enteredWord, " ")) {
                throw new CustomException("Type a one word only");
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
            writer.println("Source text - " + "\n"  + file);
            writer.println("Search by word - " + enteredWord);
            writer.println("Find matches - " + numberOfWords);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("File not exist or unwritable");
        }
        LOGGER.info("\n" + "Source text" + "\n" + file + "\n" + "Search by word - " + enteredWord + "\n" + "Find matches - " + numberOfWords);
        return numberOfWords;
    }
}

