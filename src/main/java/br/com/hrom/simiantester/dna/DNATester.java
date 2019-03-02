package br.com.hrom.simiantester.dna;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

@Component
public class DNATester {

    private static final int MIN_BASES_TO_SIMIAN = 4;
    private static List<Character> NITROGEN_BASES = asList('A', 'T', 'C', 'G');


    /**
     * Verify if a DNA belongs a dna
     *
     * @param dna DNA to be verified
     * @return true if the DNA belongs a dna, otherwise false
     */
    public boolean isSimian(String[] dna) {
        Cell[][] matrix = new Cell[dna.length][dna.length];

        for (int r = 0; r < dna.length; r++) {
            for (int c = 0; c < dna[r].length(); c++) {
                char base = dna[r].charAt(c);

                Cell cellLeft = getCellFromMatrix(r, c - 1, matrix);
                Cell cellAboveLeft = getCellFromMatrix(r - 1, c - 1, matrix);
                Cell cellAbove = getCellFromMatrix(r - 1, c, matrix);
                Cell cellAboveRight = getCellFromMatrix(r - 1, c + 1, matrix);

                int nbDuplicatedBasesLeft = cellLeft.getBase() == base ? cellLeft.getNbDuplicatedBasesLeft() + 1 : 1;
                int nbDuplicatedBasesAboveLeft = cellAboveLeft.getBase() == base ? cellAboveLeft.getNbDuplicatedBasesAboveLeft() + 1 : 1;
                int duplicatedBasesAbove = cellAbove.getBase() == base ? cellAbove.getNbDuplicatedBasesAbove() + 1 : 1;
                int duplicatedBasesAboveRight = cellAboveRight.getBase() == base ? cellAboveRight.getNbDuplicatedBasesAboveRight() + 1 : 1;

                if (nbDuplicatedBasesLeft >= MIN_BASES_TO_SIMIAN || nbDuplicatedBasesAboveLeft >= MIN_BASES_TO_SIMIAN ||
                        duplicatedBasesAbove >= MIN_BASES_TO_SIMIAN || duplicatedBasesAboveRight >= MIN_BASES_TO_SIMIAN) {
                    return true;
                }

                matrix[r][c] = new Cell(base, nbDuplicatedBasesLeft, nbDuplicatedBasesAboveLeft, duplicatedBasesAbove,
                        duplicatedBasesAboveRight);
            }
        }

        return false;
    }

    /**
     * Validate if is a valid DNA and verify if it belongs to a dna
     *
     * @param dna DNA to be validated and verified
     * @return true if the DNA belongs a dna, otherwise false
     * @throws InvalidDNAException  when the DNA has invalid format
     * @throws NullPointerException when the DNA is null
     */
    public boolean isSimianDNA(String[] dna) throws InvalidDNAException, NullPointerException {
        validate(dna);
        return isSimian(dna);
    }

    private void validate(String[] dna) throws InvalidDNAException, NullPointerException {
        requireNonNull(dna, "DNA must not be null");

        int nbBasesPerRow = dna.length;

        for (String row : dna) {
            if (row.length() != nbBasesPerRow) {
                throw new InvalidDNAException("DNA is not a valid matrix, it must be a N X N matrix");
            }

            if (row.chars().mapToObj(c -> (char) c).anyMatch(c -> !NITROGEN_BASES.contains(c))) {
                throw new InvalidDNAException("DNA have invalid nitrogen bases, the valid nitrogen bases are: A, T, C, G");
            }
        }
    }

    /**
     * Get a {@link Cell} from matrix
     *
     * @param r      row index
     * @param c      column index
     * @param matrix the matrix of {@link Cell} to get the cell
     * @return if r and c are valid indexes in matrix, return a Cell, otherwise {@link Cell#NULL} (is null value Cell)
     */
    private Cell getCellFromMatrix(int r, int c, Cell[][] matrix) {
        if (r < 0 || r > matrix.length - 1 || c < 0 || c > matrix.length - 1) {
            return Cell.NULL;
        }

        return matrix[r][c];
    }

    /**
     * Represents a cell in DNA matrix. Each cell knows how many others cells with same nitrogen base it has
     * on the left, above and on the left, above, above on the right
     */
    private static class Cell {

        /**
         * A null value Cell
         */
        private static final Cell NULL = new Cell(' ', 0, 0, 0, 0);

        //nitrogen base: A T C G
        private char base;

        private int nbDuplicatedBasesLeft;
        private int nbDuplicatedBasesAboveLeft;
        private int nbDuplicatedBasesAbove;
        private int nbDuplicatedBasesAboveRight;

        private Cell(char base, int nbDuplicatedBasesLeft, int nbDuplicatedBasesAboveLeft, int nbDuplicatedBasesAbove,
                     int nbDuplicatedBasesAboveRight) {
            this.base = base;
            this.nbDuplicatedBasesLeft = nbDuplicatedBasesLeft;
            this.nbDuplicatedBasesAboveLeft = nbDuplicatedBasesAboveLeft;
            this.nbDuplicatedBasesAbove = nbDuplicatedBasesAbove;
            this.nbDuplicatedBasesAboveRight = nbDuplicatedBasesAboveRight;
        }

        private char getBase() {
            return base;
        }

        private int getNbDuplicatedBasesLeft() {
            return nbDuplicatedBasesLeft;
        }

        private int getNbDuplicatedBasesAboveLeft() {
            return nbDuplicatedBasesAboveLeft;
        }

        private int getNbDuplicatedBasesAbove() {
            return nbDuplicatedBasesAbove;
        }

        private int getNbDuplicatedBasesAboveRight() {
            return nbDuplicatedBasesAboveRight;
        }
    }
}
