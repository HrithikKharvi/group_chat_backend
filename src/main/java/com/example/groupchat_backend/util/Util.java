package com.example.groupchat_backend.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Component
public class Util {
    public static LocalDate getCurrentDate(){
        return OffsetDateTime.now().toLocalDate();
    }

    public static String getLocalDateToStringDate(LocalDate localDate){
        return localDate.toString();
    }

    public static String getUniqueMessageId(String stringDate, BigInteger bigIntegerCounter){
        return stringDate.replace("-", "") + bigIntegerCounter.toString();
    }

}
