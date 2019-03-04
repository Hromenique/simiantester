package br.com.hrom.simiantester.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ToString
@EqualsAndHashCode
@Getter
public class StatsResponse {

    private long count_mutant_dna;
    private long count_human_dna;
    private BigDecimal ratio;

    public StatsResponse(long count_mutant_dna, long count_human_dna) {
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        this.ratio = count_human_dna == 0 ? null :
                new BigDecimal(count_mutant_dna).divide(new BigDecimal(count_human_dna), 2, RoundingMode.HALF_UP);
    }
}
