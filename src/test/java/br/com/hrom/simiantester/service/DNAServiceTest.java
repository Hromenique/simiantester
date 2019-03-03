package br.com.hrom.simiantester.service;

import br.com.hrom.simiantester.dna.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.hrom.simiantester.utils.TestUtils.formatDNA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class DNAServiceTest {

    @Mock
    private DNATester dnaTester;

    @Mock
    private DNARecordRepository repository;

    @Captor
    private ArgumentCaptor<DNARecord> dnaRecordCaptor;


    private DNAService dnaService;

    public DNAServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.dnaService = new DNAService(dnaTester, repository);
    }

    @Test
    @Parameters(method = "isSimianTestCaseValues")
    public void isSimianTest(String[] dna, boolean expectedResult, Specie expectedSpecie) {
        when(dnaTester.isSimian(eq(dna))).thenReturn(expectedResult);
        when(repository.save(any(DNARecord.class))).then(calledMethod -> calledMethod.getArgument(0));

        boolean isSimian = dnaService.isSimian(dna);

        assertThat(isSimian).as("check if the dna %s is simian", Arrays.toString(dna)).isEqualTo(expectedResult);

        InOrder inOrder = inOrder(dnaTester, repository);
        inOrder.verify(dnaTester).isSimian(dna);
        inOrder.verify(repository).save(dnaRecordCaptor.capture());
        assertThat(dnaRecordCaptor.getValue().getId()).as("check the dnaRecord's id").isNotNull();
        assertThat(dnaRecordCaptor.getValue().getSpecie()).as("check the dnaRecord's specie").isEqualTo(expectedSpecie);
    }

    @Test
    public void should_isSimian_validate_if_a_dna_is_invalid(){
        String[] dna = formatDNA(new String[]{
                "A T C G",
                "A T C G",
                "A T C G",
                "G C T X"
        });

        assertThatThrownBy(() -> dnaService.isSimian(dna)).isInstanceOf(InvalidDNAException.class);

        verify(dnaTester, never()).isSimian(any());
        verify(repository, never()).save(any(DNARecord.class));
    }

    private Object[] isSimianTestCaseValues() {
        List<Object> params = new ArrayList<>();

        String[] dna = new String[]{
                "A T C G",
                "A T C G",
                "A T C G",
                "G C T A"
        };
        params.add(new Object[]{formatDNA(dna), false, Specie.HUMAN});


        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T A G C C",
                "T T C A A A",
                "G G G T T T"
        };
        params.add(new Object[]{formatDNA(dna), true, Specie.SIMIAN});

        return params.toArray();
    }
}

