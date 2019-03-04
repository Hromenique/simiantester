package br.com.hrom.simiantester.api;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StatsResponseTest {

    @Test
    @Parameters(method = "statsResponseCaseTestValues")
    public void newStatsResponseTest(long countMutantDNA, long countHumanDNA, BigDecimal expectedRatio) {
        StatsResponse stats = new StatsResponse(countMutantDNA, countHumanDNA);

        assertThat(stats).isNotNull()
                .hasFieldOrPropertyWithValue("count_mutant_dna", countMutantDNA)
                .hasFieldOrPropertyWithValue("count_human_dna", countHumanDNA)
                .hasFieldOrPropertyWithValue("ratio", expectedRatio == null ? null : expectedRatio);
    }

    private Object[] statsResponseCaseTestValues() {
        List<Object> values = new ArrayList<>();

        values.add(new Object[]{40, 100, new BigDecimal("0.40")});
        values.add(new Object[]{100, 50, new BigDecimal("2.00")});
        values.add(new Object[]{100, 100, new BigDecimal("1.00")});
        values.add(new Object[]{1, 3, new BigDecimal("0.33")}); //infinity
        values.add(new Object[]{12, 10, new BigDecimal("1.20")});
        values.add(new Object[]{25, 100, new BigDecimal("0.25")});
        values.add(new Object[]{0, 100, new BigDecimal("0.00")});
        values.add(new Object[]{0, 0, null});
        values.add(new Object[]{100, 0, null});

        return values.toArray();
    }
}
