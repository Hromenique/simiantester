package br.com.hrom.simiantester.dna;

import org.junit.Test;

import static br.com.hrom.simiantester.utils.TestUtils.formatDNA;
import static org.assertj.core.api.Assertions.assertThat;

public class DNARecordTest {

    @Test
    public void should_construct__an_instance_from_dna() {
        String[] dna = formatDNA(new String[]{
                "A T C G",
                "A T C G",
                "A T C G",
                "G C T C"
        });

        DNARecord dnaRecord = new DNARecord(dna, Specie.HUMAN);

        assertThat(dnaRecord.getId()).as("check the dnaRecord's id").isEqualTo("ATCGATCGATCGGCTC");
        assertThat(dnaRecord.getSpecie()).as("check the dnaRecord's specie").isEqualTo(Specie.HUMAN);
    }
}
