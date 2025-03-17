package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.repository.UniqueDateIdentifier;
import com.example.groupchat_backend.repository.UniqueDateIdentifierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class UniqueDataBignumberService {

    @Autowired
    private UniqueDateIdentifierRepo uniqueDateIdentifierRepo;

    public BigInteger getNextUniqueDateIdentifier(String currentDateString){
        Optional<UniqueDateIdentifier> optionalUniqueDateIdentifier = uniqueDateIdentifierRepo.findById(currentDateString);
        UniqueDateIdentifier tempUniqueData = UniqueDateIdentifier.builder().date(currentDateString).build();

        if(optionalUniqueDateIdentifier.isEmpty()){
            tempUniqueData.setUniqueNumber(BigInteger.ONE);
        }else{
            tempUniqueData = optionalUniqueDateIdentifier.get();
            tempUniqueData.setUniqueNumber(tempUniqueData.getUniqueNumber().add(BigInteger.ONE));
        }

        uniqueDateIdentifierRepo.save(tempUniqueData);

        return tempUniqueData.getUniqueNumber();
    }
    ;
}
