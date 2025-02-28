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
        ResponseEntity<AccountResponse> post(@RequestBody @Valid CreateAccountRequest createAccountRequest); //recebe o dto

    }


