package com.company.Bankbankerservice.service;

import com.company.Bankbankerservice.dto.CreditCardDto;
import com.company.Bankbankerservice.dto.ErrorDto;
import com.company.Bankbankerservice.dto.ResponseDto;
import com.company.Bankbankerservice.repository.CreditCardRepository;
import com.company.Bankbankerservice.service.mapper.CreditCardMapper;
import com.company.Bankbankerservice.service.validate.CreditCardValidate;
import com.company.Bankbankerservice.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardService implements SimpleCrud<Integer, CreditCardDto> {
    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardValidate creditCardValidate;

    @Override
    public ResponseDto<CreditCardDto> create(CreditCardDto dto) {
        List<ErrorDto> errors = this.creditCardValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<CreditCardDto>builder()
                    .message("Credit card successful created!")
                    .success(true)
                    .data(this.creditCardMapper.toDto(this.creditCardRepository.save(this.creditCardMapper.toEntity(dto))))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("While saving error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<CreditCardDto> get(Integer id) {
        return this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id)
                .map(card -> ResponseDto.<CreditCardDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.creditCardMapper.toDto(card))
                        .build())
                .orElse(ResponseDto.<CreditCardDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<CreditCardDto> update(Integer id, CreditCardDto dto) {
        try {
            return this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id)
                    .map(card -> {
                        this.creditCardMapper.update(card, dto);
                        this.creditCardRepository.save(card);
                        return ResponseDto.<CreditCardDto>builder()
                                .data(this.creditCardMapper.toDto(card))
                                .message("Credit card successful updated!")
                                .success(true)
                                .build();
                    }).orElse(ResponseDto.<CreditCardDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("While updating error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<CreditCardDto> delete(Integer id) {
        try {
            return this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id)
                    .map(card -> {
                        card.setDeletedAt(LocalDateTime.now());
                        this.creditCardRepository.save(card);
                        return ResponseDto.<CreditCardDto>builder()
                                .data(this.creditCardMapper.toDto(card))
                                .message("Credit card successful deleted!")
                                .success(true)
                                .build();
                    })
                    .orElse(ResponseDto.<CreditCardDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    public ResponseDto<Set<CreditCardDto>> getCreditCardsByAccountId(Integer id) {
        return ResponseDto.<Set<CreditCardDto>>builder()
                .message("OK")
                .success(true)
                .data(creditCardRepository.findAllByAccountIdAndDeletedAtIsNull(id).stream().map(creditCardMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public ResponseDto<Set<CreditCardDto>> getCreditCardsByCustomerId(Integer id) {
        return ResponseDto.<Set<CreditCardDto>>builder()
                .message("OK")
                .success(true)
                .data(creditCardRepository.findByCustomerIdAndDeletedAtIsNull(id).stream().map(creditCardMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
}