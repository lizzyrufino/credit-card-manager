package desafio.altbank.spring.credit_card_manager.controllers.swagger;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
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

//swagger
@RequestMapping(value = "/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Account", description = "Account Manager")
public interface AccountControllerSwagger {
        @Operation(description = "Account creation")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Sucessfully created",
                        content = @Content(schema = @Schema(implementation = AccountResponse.class))),
                @ApiResponse(
                        responseCode = "422",
                        description = "Unprocessable Entity"),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error"),
        })
        @PostMapping
        ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest); //recebe o dto

        @Operation(description = "Recupera uma lista contas.")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista de contas")

        })
        @GetMapping
        ResponseEntity<List<AccountResponse>> listAccount(@RequestParam String cpf);

        @Operation(description = "Inativa uma conta existente")
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Conta inativada com sucesso (sem conteúdo)"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Conta não encontrada"),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erro interno no servidor")
        })
        @PatchMapping("/{accountId}/inactivate")
        ResponseEntity<AccountResponse> inactivateAccount(@PathVariable Long accountId);
    }


