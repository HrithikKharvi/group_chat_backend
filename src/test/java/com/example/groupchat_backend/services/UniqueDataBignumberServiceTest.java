package com.example.groupchat_backend.services;

import com.example.groupchat_backend.models.repository.UniqueDateIdentifier;
import com.example.groupchat_backend.repository.UniqueDateIdentifierRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniqueDataBignumberServiceTest {

    @Mock
    private UniqueDateIdentifierRepo uniqueDateIdentifierRepo;

    @InjectMocks
    @Spy
    private UniqueDataBignumberService uniqueDataBignumberService;

    @Test
    void getNextUniqueDateIdentifier_savesNewDateInteger() {
        when(uniqueDateIdentifierRepo.findByDateAndUserId(anyString(), anyString()))
                .thenReturn(Optional.empty());

        UniqueDateIdentifier mockIdentifier = new UniqueDateIdentifier();
        when(uniqueDateIdentifierRepo.save(any(UniqueDateIdentifier.class)))
                .thenReturn(mockIdentifier);

        BigInteger nextBigInteger = uniqueDataBignumberService.getNextUniqueDateIdentifier("20231212", "testUserId");

        assertNotNull(nextBigInteger);
        assertEquals(BigInteger.valueOf(1), nextBigInteger);
    }

    @Test
    void getNextUniqueDateIdentifier_returnsCount() {
        UniqueDateIdentifier currentNextIdentifier = UniqueDateIdentifier.builder().date("20231212").uniqueNumber(BigInteger.ONE).build();

        when(uniqueDateIdentifierRepo.findByDateAndUserId(anyString(), anyString()))
                .thenReturn(Optional.of(currentNextIdentifier));

        UniqueDateIdentifier mockIdentifier = new UniqueDateIdentifier();
        when(uniqueDateIdentifierRepo.save(any(UniqueDateIdentifier.class)))
                .thenReturn(mockIdentifier);

        BigInteger nextBigInteger = uniqueDataBignumberService.getNextUniqueDateIdentifier("20231212", "testUserId");

        assertNotNull(nextBigInteger);
        assertEquals(BigInteger.valueOf(2), nextBigInteger);
    }

}
