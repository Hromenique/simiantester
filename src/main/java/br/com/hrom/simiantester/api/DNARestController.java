package br.com.hrom.simiantester.api;

import br.com.hrom.simiantester.dna.InvalidDNAException;
import br.com.hrom.simiantester.dna.Specie;
import br.com.hrom.simiantester.service.DNAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin
@RestController
public class DNARestController {

    private final DNAService dnaService;

    @Autowired
    public DNARestController(DNAService dnaService) {
        this.dnaService = dnaService;
    }


    @PostMapping(path = "/simian", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity isSimian(@Valid @RequestBody DNARequest request) {
        boolean isSimian;

        try {
            isSimian = this.dnaService.isSimian(request.getDna());
        } catch (InvalidDNAException e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(new ErrorResponse(e.getMessage()));
        }

        return isSimian ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/stats", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StatsResponse getStats() {
        Map<Specie, Long> totalOfDNAsBySpecie = dnaService.getTotalOfDNAsBySpecie();

        return new StatsResponse(totalOfDNAsBySpecie.get(Specie.SIMIAN), totalOfDNAsBySpecie.get(Specie.HUMAN));
    }
}
