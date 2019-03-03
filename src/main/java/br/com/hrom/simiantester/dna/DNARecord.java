package br.com.hrom.simiantester.dna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "dnaRecords")
public class DNARecord {

    @Id
    private String id;
    private Specie specie;

    public DNARecord(String[] dna, Specie specie) {
        String id = Arrays.stream(dna).reduce("", (a, b) -> a + b);
        this.id = id;
        this.specie = specie;
    }
}
