package br.com.hrom.simiantester.simian;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitParamsRunner.class)
public class IsSimianTest {

    private IsSimian tester = new IsSimian();

    @Test
    @Parameters(method = "humanDNAValues, simianDNAValues")
    public void isSimianTest(String[] dna, boolean expectedResult) {
        assertThat(tester.isSimian(dna)).as("check if the dna %s is simian", Arrays.toString(dna))
                .isEqualTo(expectedResult);
    }

    private Object[] humanDNAValues() throws Exception {
        List<String[]> dnas = readDNAFile("humanDNAs.txt");
        Object[] params = new Object[dnas.size()];

        for (int i = 0; i < dnas.size(); i++) {
            params[i] = new Object[]{dnas.get(i), false};
        }

        return params;
    }

    private Object[] simianDNAValues() throws Exception {
        List<String[]> dnas = readDNAFile("simianDNAs.txt");
        Object[] params = new Object[dnas.size()];

        for (int i = 0; i < dnas.size(); i++) {
            params[i] = new Object[]{dnas.get(i), true};
        }

        return params;
    }

    private List<String[]> readDNAFile(String filePath) throws Exception {
        String path = new ClassPathResource(filePath).getFile().getPath();
        List<String> lines = Files.readAllLines(Paths.get(path));

        List<String[]> dnas = new ArrayList<>();
        List<String> dna = new ArrayList<>();

        for (String line : lines) {
            if (!line.equals("")) {
                dna.add(line);
            } else {
                String[] newDNA = dna.stream().map(l -> l.replaceAll(" ", "")).toArray(String[]::new);
                dnas.add(newDNA);
                dna = new ArrayList<>();
            }
        }

        return dnas;
    }
}
