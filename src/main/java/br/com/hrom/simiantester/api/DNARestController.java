package br.com.hrom.simiantester.api;

import br.com.hrom.simiantester.dna.InvalidDNAException;
import br.com.hrom.simiantester.service.DNAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DNARestController {

    private final DNAService dnaService;

    @Autowired
    public DNARestController(DNAService dnaService) {
        this.dnaService = dnaService;
    }


    @PostMapping(path = "/simian", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity isSimian(@RequestBody DNARequest request) {
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
}
