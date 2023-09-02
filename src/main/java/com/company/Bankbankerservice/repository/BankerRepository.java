package com.company.Bankbankerservice.repository;

import com.company.Bankbankerservice.entity.Banker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BankerRepository extends CrudRepository<Banker,Integer> {
    Optional<Banker> findByBankerIdAndDeletedAtIsNull(Integer id);
    Boolean existsByBankerName(String name);
    Set<Banker>findAllByBranchIdAndDeletedAtIsNull(Integer id);
}
