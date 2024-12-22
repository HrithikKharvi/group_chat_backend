package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.TempTable;
import com.example.groupchat_backend.models.UniqueDateIdentifier;
import com.example.groupchat_backend.repository.TempTableRepo;
import com.example.groupchat_backend.repository.UniqueDateIdentifierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempTableService {

    @Autowired
    private TempTableRepo tempTableRepo;

    @Autowired
    private UniqueDateIdentifierRepo uniqueDateIdentifierRepo;

    public List<UniqueDateIdentifier> getAllUniqueIdentifier(){
        return uniqueDateIdentifierRepo.findAll();
    }
    public List<TempTable> getTempTable(){
        return tempTableRepo.findAll();
    }

}

