package com.company.Bankbankerservice.controller;

import com.company.Bankbankerservice.dto.CreditCardDto;
import com.company.Bankbankerservice.dto.ResponseDto;
import com.company.Bankbankerservice.service.CreditCardService;
import com.company.Bankbankerservice.util.SimpleCrud;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("credit-card")
@RequiredArgsConstructor
@Slf4j
@RateLimiter(name = "first-rate-limiter",fallbackMethod = "fallBack")
public class CreditCardController implements SimpleCrud<Integer, CreditCardDto> {
    private final CreditCardService creditCardService;
    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create card method!",
            description = "This is method for create card!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CreditCardDto.class
                            )
                    )
            )
    )
    public ResponseDto<CreditCardDto> create(@Valid @RequestBody CreditCardDto dto) {
        return this.creditCardService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get by card method!",
            description = "This is method for get card!"
    )
    public ResponseDto<CreditCardDto> get(@PathVariable(value = "id") Integer id) {
        return this.creditCardService.get(id);
    }
    @GetMapping("/get-by-account/{id}")
    @Operation(
            tags = "Get",
            summary = "Your summary get by card method!",
            description = "This is method for get card!"
    )
    public ResponseDto<Set<CreditCardDto>> getCreditCardsByAccountId(@PathVariable(value = "id") Integer id){
        return this.creditCardService.getCreditCardsByAccountId(id);
    }
    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update card method!",
            description = "This is method for update card!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CreditCardDto.class
                            )
                    )
            )
    )
    public ResponseDto<CreditCardDto> update(@PathVariable(value = "id") Integer id, @RequestBody CreditCardDto dto) {
        return this.creditCardService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by card method!",
            description = "This is method for delete card!"
    )
    public ResponseDto<CreditCardDto> delete(@PathVariable(value = "id") Integer id) {
        return this.creditCardService.delete(id);
    }
    public ResponseDto<CreditCardDto> fallBack(Exception e){
        log.warn("inti fallBack method");
        return ResponseDto.<CreditCardDto>builder()
                .message("inti fallBack method")
                .build();
    }
}