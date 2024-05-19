package org.lgp.loader;

import lombok.Getter;
import org.lgp.TemplateCache;

import java.io.InputStream;

@Getter
public class DownloadTemplateSource {
    private TemplateCache templateCache;
    private String name;

    public DownloadTemplateSource(String name, TemplateCache templateCache) {
        this.templateCache = templateCache;
        this.name = name;
    }

    public InputStream getInputStream() {
        return templateCache.get(this.name);
    }


}
