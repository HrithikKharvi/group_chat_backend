package com.example.groupchat_backend.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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

    @Column(name="unique_number")
    private BigInteger uniqueNumber;
}
