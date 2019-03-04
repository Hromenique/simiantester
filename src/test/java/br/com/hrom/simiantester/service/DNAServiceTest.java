package br.com.hrom.simiantester.service;

import br.com.hrom.simiantester.dna.*;
import br.com.hrom.simiantester.dna.DNARecordRepository.DNACount;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.*;

import static br.com.hrom.simiantester.dna.Specie.HUMAN;
import static br.com.hrom.simiantester.dna.Specie.SIMIAN;
import static br.com.hrom.simiantester.utils.TestUtils.formatDNA;
import static java.util.Arrays.asList;
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
    public void should_isSimian_validate_if_a_dna_is_invalid() {
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

    @Test
    @Parameters(method = "getTotalOfDNAsBySpecieTestCaseValues")
    public void getTotalOfDNAsBySpecieTest(List<DNACount> dnaCountsFromRepository, long expectedTotalOfDNAsHuman,
                                           long expectedTotalOfDNAsSimian) {
        when(repository.totalsBySpecie()).thenReturn(dnaCountsFromRepository);

        Map<Specie, Long> totalOfDNAsBySpecie = dnaService.getTotalOfDNAsBySpecie();

        assertThat(totalOfDNAsBySpecie.get(HUMAN)).as("check the total of human's DNAs")
                .isEqualTo(expectedTotalOfDNAsHuman);
        assertThat(totalOfDNAsBySpecie.get(SIMIAN)).as("check the total of simian's DNAs")
                .isEqualTo(expectedTotalOfDNAsSimian);

        verify(repository).totalsBySpecie();
    }

    private Object[] isSimianTestCaseValues() {
        List<Object> values = new ArrayList<>();

        String[] dna = new String[]{
                "A T C G",
                "A T C G",
                "A T C G",
                "G C T A"
        };
        values.add(new Object[]{formatDNA(dna), false, HUMAN});


        dna = new String[]{
                "A T C G A A",
                "T A C A A A",
                "G G A A C C",
                "T T A G C C",
                "T T C A A A",
                "G G G T T T"
        };
        values.add(new Object[]{formatDNA(dna), true, SIMIAN});

        return values.toArray();
    }

    private Object[] getTotalOfDNAsBySpecieTestCaseValues(){
        List<Object> values = new ArrayList<>();

        List<DNACount> dnaCounts = asList(new DNACount(HUMAN.name(), 10), new DNACount(SIMIAN.name(), 15));
        values.add(new Object[] {dnaCounts, 10, 15});

        dnaCounts = asList(new DNACount(HUMAN.name(), 10));
        values.add(new Object[] {dnaCounts, 10, 0});

        dnaCounts = asList(new DNACount(SIMIAN.name(), 15));
        values.add(new Object[] {dnaCounts, 0, 15});

        values.add(new Object[] {Collections.emptyList(), 0, 0});

        return values.toArray();
    }
}

