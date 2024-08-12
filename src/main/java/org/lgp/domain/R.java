package org.lgp.domain;

import lombok.Data;

@Data
public class R<T> {
    private String code;
    private String message;
    private T data;

    public static <T> R<T> ok(T t) {
        R<T> r = new R<>();
        r.setCode("200");
        r.setMessage("执行成功");
        r.setData(t);
        return r;
    }

    public static <T> R<T> ok() {
        return ok(null);
    }
}
