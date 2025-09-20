package com.example.groupchat_backend.models.repository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.math.BigInteger;

@Entity(name="date_bignumber_unique")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniqueDateIdentifier {
    @Id
    @Column(name="date_field")
    private String date;

    @Column(name="user_id")
    private String userId;

    @Column(name="unique_number")
    private BigInteger uniqueNumber;
}
