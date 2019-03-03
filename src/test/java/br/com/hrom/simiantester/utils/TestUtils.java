package br.com.hrom.simiantester.utils;

import java.util.Arrays;

public class TestUtils {

    public static String[] formatDNA(String[] dna) {
        return Arrays.stream(dna).map(l -> l.replaceAll(" ", "")).toArray(String[]::new);
    }
}
