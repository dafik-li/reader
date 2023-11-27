package com.solvd.reader.execute;

import com.solvd.reader.exception.ChooseMethodException;
import com.solvd.reader.generate.Generator;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws ChooseMethodException, IOException {
        Generator generator = new Generator();
        generator.chooseMethod();
        generator.countUniqueWords();
        generator.countLetters();
        generator.findMatches();
    }




}
