package com.example.groupchat_backend.DataMappers;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class MessageModelMapper {
    @NonNull
    private String message;
    @Nullable
    private LocalDate sentDate;
}
