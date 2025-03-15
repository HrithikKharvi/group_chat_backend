package com.example.groupchat_backend.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class Util {
    public static LocalDateTime getCurrentDateTime(){
        return OffsetDateTime.now().toLocalDateTime();
    }

    public static String getLocalDateToStringDate(LocalDateTime localDate){
        return localDate.toString();
    }

    public static String getUniqueMessageId(String stringDate, BigInteger bigIntegerCounter){
        return stringDate.replace("-", "") + bigIntegerCounter.toString();
    }

}
