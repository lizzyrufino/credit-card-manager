package desafio.altbank.spring.credit_card_manager.controllers.swagger;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateCreditCardRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.CreditCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/credit-card", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Credit Card", description = "Credit Card Manager")
public interface CreditCardControllerSwagger {
    @Operation(description = "Criar um novo cartao de credito")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CreditCardResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<CreditCardResponse> createCreditCard(@RequestBody @Valid Long accountId); //recebe o accountId

    @Operation(description = "Reemite um cartao de credito")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cartao Reemitido com sucesso"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor")
    })
    @PatchMapping("/{creditCardId}/reissue")
    ResponseEntity<Void> reissueCard(@PathVariable Long creditCardId,String reason );

    @Operation(description = "Ativa um cartao de credito existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cartao ativado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cartao não encontrado"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor")
    })
    @PatchMapping("/{creditCardId}/activate")
    ResponseEntity<Void> activateCard(@PathVariable Long creditCardId);

    @Operation(description = "Bloqueia um cartao de credito existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cartao bloqueado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cartao não encontrado"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor")
    })
    @PatchMapping("/{creditCardId}/block")
    ResponseEntity<Void> blockCreditCard(@PathVariable Long creditCardId);

    @Operation(description = "Recupera uma lista de cartoes.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cartoes")

    })
    @GetMapping
    ResponseEntity<List<CreditCardResponse>> listCards(@RequestParam Long accountId);

}

