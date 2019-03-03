package br.com.hrom.simiantester.dna;

import br.com.hrom.simiantester.utils.TestUtils;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class DNAValidatorTest {

    @Test
    @Parameters(method = "validCaseValues")
    public void should_not_throw_exception_when_dna_is_valid(String[] dna){
        DNAValidator.isValidDNA(dna);
    }

    @Test
    @Parameters(method = "exceptionCaseValues")
    public void should_trow_exception_when_dna_is_invalid(String[] dna, Class<Exception> typeOfException,
                                                                String exceptionMessage) {
        assertThatThrownBy(() -> DNAValidator.isValidDNA(dna)).isInstanceOf(typeOfException).hasMessage(exceptionMessage);
    }

    private Object[] validCaseValues(){
        List<Object> params = new ArrayList<>();

        String[] dna = new String[]{
                "A"
        };
        params.add(dna);

        dna = new String[]{
                "A A A",
                "C C C",
                "G G G"
        };
        params.add(TestUtils.formatDNA(dna));

        dna = new String[]{
                "A A A T",
                "A T T T",
                "C C C G",
                "C G G G"
        };
        params.add(TestUtils.formatDNA(dna));

        dna = new String[]{
                "A T C G",
                "A T C G",
                "A T C G",
                "G C T A"
        };
        params.add(TestUtils.formatDNA(dna));

        return params.toArray();
    }

    private Object[] exceptionCaseValues(){
        List<Object> params = new ArrayList<>();
        String[] dna;

        dna = new String[]{
                "A T C G A A",
                "T A Z A A A",
                "G G A A C C",
                "T T G G C C",
                "T T C G A A",
                "G G G T G T"
        };
        params.add(new Object[]{TestUtils.formatDNA(dna), InvalidDNAException.class,
                "DNA does not have valid nitrogen bases"});

        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T G G C",
                "T T C G A A",
                "G G G T G T"
        };
        params.add(new Object[]{TestUtils.formatDNA(dna), InvalidDNAException.class,
                "DNA is not a valid matrix, it must be a N X N matrix"});


        return params.toArray();
    }
}
