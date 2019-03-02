package br.com.hrom.simiantester.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private List<String> messages;

    public ErrorResponse(String message){
        this.messages = Arrays.asList(message);
    }
}
