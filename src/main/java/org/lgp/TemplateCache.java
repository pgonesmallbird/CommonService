package org.lgp;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TemplateCache {
    private Map<String, InputStream> cache = new ConcurrentHashMap<>();

    public InputStream get(String name) {
        return cache.get(name.toLowerCase(Locale.ROOT));
    }

    public void put(String name, InputStream inputStream) {
        cache.put(name.toLowerCase(Locale.ROOT), inputStream);
    }
}
