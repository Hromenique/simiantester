package br.com.hrom.simiantester.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class StatsResponse {

    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;

    public StatsResponse(long count_mutant_dna, long count_human_dna) {
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        this.ratio = (double) count_mutant_dna / count_human_dna;
    }
}
