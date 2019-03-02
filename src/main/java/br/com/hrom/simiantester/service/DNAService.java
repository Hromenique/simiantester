package br.com.hrom.simiantester.service;

import br.com.hrom.simiantester.dna.DNATester;
import br.com.hrom.simiantester.dna.InvalidDNAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNAService {

    private final DNATester dnaTester;

    @Autowired
    public DNAService(DNATester dnaTester) {
        this.dnaTester = dnaTester;
    }

    public boolean isSimian(String[] dna) throws InvalidDNAException {
        return dnaTester.isSimianDNA(dna);
    }
}
