package br.com.hrom.simiantester.simian;

public class IsSimian {

    private static final int MIN_BASES_TO_SIMIAN = 4;

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


    private Cell getCellFromMatrix(int r, int c, Cell[][] matrix) {
        if (r < 0 || r > matrix.length - 1 || c < 0 || c > matrix.length - 1) {
            return Cell.NULL;
        }

        return matrix[r][c];
    }

    private static class Cell {

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
