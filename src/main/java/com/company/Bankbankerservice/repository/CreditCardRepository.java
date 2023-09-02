package com.company.Bankbankerservice.repository;

import com.company.Bankbankerservice.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {
    Optional<CreditCard> findByCreditCardIdAndDeletedAtIsNull(Integer id);
    Boolean existsByNumber(Long number);
    Set<CreditCard> findAllByAccountIdAndDeletedAtIsNull(Integer id);
    Set<CreditCard> findByCustomerIdAndDeletedAtIsNull(Integer id);
}
