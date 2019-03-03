package br.com.hrom.simiantester.dna;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

public class DNAValidator {

    private static List<Character> NITROGEN_BASES = asList('A', 'T', 'C', 'G');

    public static void isValidDNA(String[] dna) throws InvalidDNAException {
        requireNonNull(dna);

        int nbBasesPerRow = dna.length;

        for (String row : dna) {
            if (row.length() != nbBasesPerRow) {
                throw new InvalidDNAException("DNA is not a valid matrix, it must be a N X N matrix");
            }

            if (notContainsNitrogenBases(row)) {
                throw new InvalidDNAException("DNA does not have valid nitrogen bases");
            }
        }
    }

    private static boolean notContainsNitrogenBases(String row) {
        return row.chars().anyMatch(c -> !NITROGEN_BASES.contains((char)c));
    }
}
