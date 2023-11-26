package com.solvd.reader.execute;

import com.solvd.reader.generate.Generator;

public class Main {

    public static void main(String[] args)
    {
        Generator generator = new Generator();
        generator.readFile();
        generator.countUniqueWords();
        generator.countLetters();
        generator.findMatches();
    }




}
