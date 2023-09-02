package com.company.Bankbankerservice.controller;

import com.company.Bankbankerservice.dto.BankerDto;
import com.company.Bankbankerservice.dto.CreditCardDto;
import com.company.Bankbankerservice.dto.ResponseDto;
import com.company.Bankbankerservice.service.BankerService;
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
@RequestMapping("banker")
@RequiredArgsConstructor
@Slf4j
@RateLimiter(name = "first-rate-limiter", fallbackMethod = "fallBack")
public class BankerController implements SimpleCrud<Integer, BankerDto> {
    private final BankerService bankerService;
    private final CreditCardService creditCardService;

    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create banker method!",
            description = "This is method for create banker!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = BankerDto.class
                            )
                    )
            )
    )
    public ResponseDto<BankerDto> create(@Valid @RequestBody BankerDto dto) {
        return this.bankerService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get method!",
            description = "This is method for get banker!"
    )
    public ResponseDto<BankerDto> get(@PathVariable(value = "id") Integer id) {
        return this.bankerService.get(id);
    }

    @GetMapping("/get-by-branch/{id}")
    @Operation(
            tags = "Get",
            summary = "Your summary get by branch method!",
            description = "This is method for get banker!"
    )
    public ResponseDto<Set<BankerDto>> getByBranch(@PathVariable(value = "id") Integer id) {
        return this.bankerService.getByBranch(id);
    }

    public ResponseDto<Set<CreditCardDto>> getCreditCardsByCustomerId(@PathVariable(value = "id") Integer id) {
        return this.creditCardService.getCreditCardsByCustomerId(id);
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update banker method!",
            description = "This is method for update banker!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = BankerDto.class
                            )
                    )
            )
    )
    public ResponseDto<BankerDto> update(@PathVariable(value = "id") Integer id, @RequestBody BankerDto dto) {
        return this.bankerService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by banker method!",
            description = "This is method for delete banker!"
    )
    public ResponseDto<BankerDto> delete(@PathVariable(value = "id") Integer id) {
        return this.bankerService.delete(id);
    }

    public ResponseDto<BankerDto> fallBack(Exception e) {
        log.warn("inti fallBack method");
        return ResponseDto.<BankerDto>builder()
                .message("inti fallBack method")
                .build();
    }
}