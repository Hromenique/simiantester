package br.com.hrom.simiantester.api;

import br.com.hrom.simiantester.dna.InvalidDNAException;
import br.com.hrom.simiantester.service.DNAService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({DNARestController.class})
public class DNARestControllerTest {

    @Autowired
    private MockMvc tester;

    @MockBean
    private DNAService dnaService;

    @Test
    public void should_response_with_200_OK_when_dna_is_simian() throws Exception {
        String[] expectedDNA = {"ATGCGA", "CAGTGC", "TTATTT", "AGAAGG", "GCGTCA", "TCACTG"};
        when(dnaService.isSimian(expectedDNA)).thenReturn(true);

        String request = new StringBuilder()
                .append("{")
                    .append("\"dna\": [")
                        .append("\"ATGCGA\", ")
                        .append("\"CAGTGC\", ")
                        .append("\"TTATTT\", ")
                        .append("\"AGAAGG\", ")
                        .append("\"GCGTCA\", ")
                        .append("\"TCACTG\"")
                    .append("]")
                .append("}").toString();

        tester.perform(
                post("/simian").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(isEmptyOrNullString()));

        verify(dnaService).isSimian(expectedDNA);
    }

    @Test
    public void should_response_with_403_FORBIDDEN_when_dna_is_not_simian() throws Exception {
        String[] expectedDNA = {"ATGCGA", "CAGTGC", "TTATTT", "AGAAGG", "GCGTCA", "TCACTG"};
        when(dnaService.isSimian(expectedDNA)).thenReturn(false);

        String request = new StringBuilder()
                .append("{")
                    .append("\"dna\": [")
                        .append("\"ATGCGA\", ")
                        .append("\"CAGTGC\", ")
                        .append("\"TTATTT\", ")
                        .append("\"AGAAGG\", ")
                        .append("\"GCGTCA\", ")
                        .append("\"TCACTG\"")
                    .append("]")
                .append("}").toString();

        tester.perform(
                post("/simian").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));

        verify(dnaService).isSimian(expectedDNA);
    }

    @Test
    public void should_response_with_404_BAD_REQUEST_when_dna_is_invalid() throws Exception {
        String[] expectedDNA = {"ATGCGA", "CAGTGC", "TTATTT", "AGAAGG", "GCGTCA"}; //6X5 matrix
        when(dnaService.isSimian(expectedDNA)).thenThrow(new InvalidDNAException("invalid DNA"));

        String request = new StringBuilder()
                .append("{")
                    .append("\"dna\": [")
                        .append("\"ATGCGA\", ")
                        .append("\"CAGTGC\", ")
                        .append("\"TTATTT\", ")
                        .append("\"AGAAGG\", ")
                        .append("\"GCGTCA\" ")
                    .append("]")
                .append("}").toString();

        tester.perform(
                post("/simian").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"messages\":[\"invalid DNA\"]}"));

        verify(dnaService).isSimian(expectedDNA);
    }


}
