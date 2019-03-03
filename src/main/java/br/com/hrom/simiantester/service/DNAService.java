package br.com.hrom.simiantester.service;

import br.com.hrom.simiantester.dna.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNAService {

    private final DNATester dnaTester;
    private final DNARecordRepository repository;

    @Autowired
    public DNAService(DNATester dnaTester, DNARecordRepository repository) {
        this.dnaTester = dnaTester;
        this.repository = repository;
    }

    public boolean isSimian(String[] dna) throws InvalidDNAException {
        boolean isSimian = dnaTester.isSimianDNA(dna);

        DNARecord dnaRecord = new DNARecord(dna, isSimian ? Specie.SIMIAN : Specie.HUMAN);
        repository.save(dnaRecord);

        return isSimian;

    }
}
