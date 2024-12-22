package com.example.groupchat_backend.repository;

import com.example.groupchat_backend.models.UniqueDateIdentifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;


import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class TestUniqueDateIdentifierRepo {

    @Autowired
    private UniqueDateIdentifierRepo uniqueDateIdentifierRepo;

    @Test
    public void testFindAll() {
        UniqueDateIdentifier uniqueDate1 = UniqueDateIdentifier.builder()
                .date("23-01-2024").uniqueNumber(BigInteger.valueOf(1223343L))
                .build();
        UniqueDateIdentifier uniqueDate2 = UniqueDateIdentifier.builder()
                .date("23-01-2023").uniqueNumber(BigInteger.valueOf(1223343L))
                .build();

        uniqueDateIdentifierRepo.save(uniqueDate1);
        uniqueDateIdentifierRepo.save(uniqueDate2);

        List<UniqueDateIdentifier> allUniqueIds = uniqueDateIdentifierRepo.findAll();

        assertEquals(2, allUniqueIds.size());
    }
}
