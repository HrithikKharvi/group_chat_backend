package com.example.groupchat_backend.helpers.shared;

import java.util.function.Function;

public final class AppFunctions{

    public static Function<Integer, Integer> GET_NEXT_INTEGER_COUNT = currentCount -> currentCount == null ? 0 : currentCount;

}
