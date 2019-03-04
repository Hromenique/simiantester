package br.com.hrom.simiantester.service;

import br.com.hrom.simiantester.dna.*;
import br.com.hrom.simiantester.dna.DNARecordRepository.DNACount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static br.com.hrom.simiantester.dna.DNAValidator.isValidDNA;

@Service
public class DNAService {

    private final DNATester dnaTester;
    private final DNARecordRepository dnaRepository;

    @Autowired
    public DNAService(DNATester dnaTester, DNARecordRepository dnaRepository) {
        this.dnaTester = dnaTester;
        this.dnaRepository = dnaRepository;
    }

    public boolean isSimian(String[] dna) throws InvalidDNAException {
        isValidDNA(dna);
        boolean isSimian = dnaTester.isSimian(dna);

        DNARecord dnaRecord = new DNARecord(dna, isSimian ? Specie.SIMIAN : Specie.HUMAN);
        dnaRepository.save(dnaRecord);

        return isSimian;
    }

    public Map<Specie, Long> getTotalOfDNAsBySpecie() {
        Map<Specie, Long> totalOfDNAsBySpecie = new HashMap<>();
        totalOfDNAsBySpecie.putIfAbsent(Specie.SIMIAN, 0L);
        totalOfDNAsBySpecie.putIfAbsent(Specie.HUMAN, 0L);

        for (DNACount count : this.dnaRepository.totalsBySpecie()) {
            totalOfDNAsBySpecie.put(Specie.valueOf(count.getId()), count.getTotal());
        }

        return totalOfDNAsBySpecie;
    }
}
