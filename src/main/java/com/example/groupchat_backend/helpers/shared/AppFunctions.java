package com.example.groupchat_backend.helpers.shared;

import com.example.groupchat_backend.constants.CommonAppData;
import com.example.groupchat_backend.models.message.baseClasses.RawMessageDTO;

import java.math.BigInteger;
import java.util.function.Function;

public final class AppFunctions{

    public static Function<Integer, Integer> GET_NEXT_INTEGER_COUNT = currentCount -> currentCount == null ? 0 : currentCount;
    public static Function<String, Boolean> IS_EMPTY_STRING = str -> str == null || str.isEmpty();
    public static String GENERATE_MESSAGE_UNIQUE_ID(BigInteger nextBigIntegerForToday, RawMessageDTO rawMessageDTO){
        return rawMessageDTO.getSentByUserId() + CommonAppData.HYPHEN + nextBigIntegerForToday;
    }
}
