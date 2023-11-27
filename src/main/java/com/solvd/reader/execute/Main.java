package com.solvd.reader.execute;

import com.solvd.reader.exception.ChooseMethodException;
import com.solvd.reader.generate.Generator;

public class Main {

    public static void main(String[] args) throws ChooseMethodException {
        Generator generator = new Generator();
        generator.chooseMethod();
        generator.countUniqueWords();
        generator.countLetters();
        generator.findMatches();
    }




}
