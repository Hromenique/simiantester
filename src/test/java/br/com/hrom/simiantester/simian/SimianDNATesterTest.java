package br.com.hrom.simiantester.simian;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitParamsRunner.class)
public class SimianDNATesterTest {

    private SimianDNATester tester = new SimianDNATester();

    @Test
    @Parameters(method = "humanDNAValues, simianDNAValues")
    public void isSimianTest(String[] dna, boolean expectedResult) {
        assertThat(tester.isSimian(dna)).as("check if the dna %s is simian", Arrays.toString(dna))
                .isEqualTo(expectedResult);
    }

    private Object[] humanDNAValues() {
        List<Object> params = new ArrayList<>();
        String[] dna;

        dna = new String[]{
                "A"
        };
        params.add(dnaAndExpectedResult(dna, false));

        dna = new String[]{
                "A A A",
                "C C C",
                "G G G"
        };
        params.add(dnaAndExpectedResult(dna, false));

        dna = new String[]{
                "A A A T",
                "A T T T",
                "C C C G",
                "C G G G"
        };
        params.add(dnaAndExpectedResult(dna, false));

        dna = new String[]{
                "A T C G",
                "A T C G",
                "A T C G",
                "G C T A"
        };
        params.add(dnaAndExpectedResult(dna, false));

        dna = new String[]{
                "A T C C",
                "T A C G",
                "T C A G",
                "T T T G"
        };
        params.add(dnaAndExpectedResult(dna, false));

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T T C C C",
                "T T C A A A",
                "G G G T T T"
        };
        params.add(dnaAndExpectedResult(dna, false));

        dna = new String[]{
                "A T G C G A",
                "C A G T G C",
                "T T A T T T",
                "A G A C G G",
                "G C G T C A",
                "T C A C T G"};
        params.add(dnaAndExpectedResult(dna, false));

        return params.toArray();
    }

    private Object[] simianDNAValues() {
        List<Object> params = new ArrayList<>();
        String[] dna;

        dna = new String[]{
                "C T G A G A",
                "C C C C T G",
                "T A T T G T",
                "A G A G G G",
                "C C C A T G",
                "A A A G G G"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T T C C C",
                "T T C A A A",
                "G G T T T T"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "T G A A C C",
                "T T T C C C",
                "T T C A A A",
                "G G G T T T"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T C G A A",
                "T A C A C A",
                "G G A A C C",
                "T T T C C C",
                "T T C A C A",
                "G G G T T T"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "C T G A G A",
                "C T A T G C",
                "T A T T G T",
                "A G A G G G",
                "C C C C T A",
                "T C A C T G"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T T A C C",
                "T T C A A A",
                "G G G T T T"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T A G C C",
                "T T C A A A",
                "G G G T T T"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T G G C C",
                "T T C G A A",
                "G G G T G T"
        };
        params.add(dnaAndExpectedResult(dna, true));

        dna = new String[]{
                "A T G C G A",
                "C A G T G C",
                "T T A T T T",
                "A G A A G G",
                "G C G T C A",
                "T C A C T G"};
        params.add(dnaAndExpectedResult(dna, true));


        return params.toArray();
    }

    private Object[] dnaAndExpectedResult(String[] dna, boolean expectedResult) {
        return new Object[]{formatDNA(dna), expectedResult};
    }

    private String[] formatDNA(String[] dna) {
        return Arrays.stream(dna).map(l -> l.replaceAll(" ", "")).toArray(String[]::new);
    }

}
