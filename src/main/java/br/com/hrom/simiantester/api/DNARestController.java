package br.com.hrom.simiantester.api;

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


    @PostMapping(path = "/dna", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity isSimian(@RequestBody DNARequest request) {

        boolean isSimian = this.dnaService.isSimian(request.getDna());

        if (isSimian) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
