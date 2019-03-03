package br.com.hrom.simiantester.dna;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DNARecordRepository extends MongoRepository<DNARecord, String> {
}
