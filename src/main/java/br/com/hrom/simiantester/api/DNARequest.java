package br.com.hrom.simiantester.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DNARequest {
    @NotEmpty
    private String[] dna;
}
