package com.br.condomio.apt.resource;

import com.br.condomio.apt.dto.CepDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("util")
public class UtilResource {


    @Operation(summary = "Busca endereço por cep")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço retornado"
            ),
            @ApiResponse(responseCode = "400", description = "gerou exceção",
                    content = @Content),
    })
    @GetMapping(path = "/cep")
    public ResponseEntity<CepDTO> buscarCEP(@RequestParam(value = "value") String cep) {
        RestTemplate restTemplate = new RestTemplate();
        var bsuca = cep.replaceAll("[^0-9]", "");
        try {
            return ResponseEntity.ok(restTemplate.getForObject("https://viacep.com.br/ws/" + bsuca + "/json/", CepDTO.class));
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.badRequest().build();
        }


    }
}
