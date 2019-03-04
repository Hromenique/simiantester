package br.com.hrom.simiantester.dna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
public class DNARecordRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DNARecordRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public DNARecord save(DNARecord record) {
        return this.mongoTemplate.save(record);
    }

    public List<DNACount> totalsBySpecie() {
        AggregationResults<DNACount> results = this.mongoTemplate
                .aggregate(newAggregation(group("specie").count().as("total")), "dnaRecords", DNACount.class);

        return results.getMappedResults();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DNACount {
        private String id;
        private long total;
    }
}
