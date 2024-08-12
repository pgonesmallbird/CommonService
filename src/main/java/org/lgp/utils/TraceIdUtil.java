package org.lgp.utils;

import java.util.UUID;

public class TraceIdUtil {
    public static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
