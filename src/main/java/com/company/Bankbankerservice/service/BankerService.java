package com.company.Bankbankerservice.service;

import com.company.Bankbankerservice.dto.BankerDto;
import com.company.Bankbankerservice.dto.ErrorDto;
import com.company.Bankbankerservice.dto.ResponseDto;
import com.company.Bankbankerservice.repository.BankerRepository;
import com.company.Bankbankerservice.service.mapper.BankerMapper;
import com.company.Bankbankerservice.service.validate.BankerValidate;
import com.company.Bankbankerservice.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankerService implements SimpleCrud<Integer, BankerDto> {
    private final BankerRepository bankerRepository;
    private final BankerMapper bankerMapper;
    private final BankerValidate bankerValidate;

    @Override
    public ResponseDto<BankerDto> create(BankerDto dto) {
        List<ErrorDto> errors = this.bankerValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<BankerDto>builder()
                    .message("Validate error!")
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<BankerDto>builder()
                    .message("Banker successful created!")
                    .success(true)
                    .data(this.bankerMapper.toDto(bankerRepository.save(bankerMapper.toEntity(dto))))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<BankerDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<BankerDto> get(Integer id) {
        return this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id)
                .map(banker -> ResponseDto.<BankerDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.bankerMapper.toDto(banker))
                        .build())
                .orElse(ResponseDto.<BankerDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<BankerDto> update(Integer id, BankerDto dto) {
        try {
            return this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id)
                    .map(banker -> {
                        this.bankerMapper.update(banker, dto);
                        this.bankerRepository.save(banker);
                        return ResponseDto.<BankerDto>builder()
                                .message("Banker successful updated!")
                                .success(true)
                                .data(this.bankerMapper.toDto(banker))
                                .build();
                    })
                    .orElse(ResponseDto.<BankerDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<BankerDto>builder()
                    .message("While updating error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<BankerDto> delete(Integer id) {
        try {
            return this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id)
                    .map(banker -> {
                        banker.setDeletedAt(LocalDateTime.now());
                        this.bankerRepository.save(banker);
                        return ResponseDto.<BankerDto>builder()
                                .message("Banker successful deleted!")
                                .success(true)
                                .data(this.bankerMapper.toDto(banker))
                                .build();
                    })
                    .orElse(ResponseDto.<BankerDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<BankerDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    public ResponseDto<Set<BankerDto>> getByBranch(Integer id) {
        return ResponseDto.<Set<BankerDto>>builder()
                .message("OK")
                .success(true)
                .data(bankerRepository.findAllByBranchIdAndDeletedAtIsNull(id).stream().map(bankerMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
}
