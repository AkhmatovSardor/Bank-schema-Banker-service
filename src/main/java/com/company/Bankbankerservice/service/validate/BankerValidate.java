package com.company.Bankbankerservice.service.validate;

import com.company.Bankbankerservice.dto.BankerDto;
import com.company.Bankbankerservice.dto.ErrorDto;
import com.company.Bankbankerservice.repository.BankerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BankerValidate {
    private final BankerRepository bankerRepository;
   // private final BranchService branchService;
    public List<ErrorDto> validate(BankerDto dto) {
        List<ErrorDto> errors = new ArrayList<>();
        if (this.bankerRepository.existsByBankerName(dto.getBankerName())) {
            errors.add(new ErrorDto("banker name", "banker name already exists!"));
        }
     //   if (branchService.get(dto.getBranchId()).getData()==null){
       //     errors.add(new ErrorDto("branch","branch not found!"));
        //}
        return errors;
    }
}
