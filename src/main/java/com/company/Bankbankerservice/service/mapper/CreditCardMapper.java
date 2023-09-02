package com.company.Bankbankerservice.service.mapper;

import com.company.Bankbankerservice.dto.CreditCardDto;
import com.company.Bankbankerservice.entity.CreditCard;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class CreditCardMapper {
    public abstract CreditCardDto toDto(CreditCard creditCard);

    @Mapping(target = "creditCardId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract CreditCard toEntity(CreditCardDto dto);

    @Mapping(target = "creditCardId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget CreditCard card, CreditCardDto dto);
}
