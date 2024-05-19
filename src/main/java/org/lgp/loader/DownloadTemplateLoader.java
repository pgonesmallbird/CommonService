package org.lgp.loader;

import freemarker.cache.TemplateLoader;
import org.lgp.TemplateCache;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class DownloadTemplateLoader implements TemplateLoader {
    private TemplateCache templateCache;

    public DownloadTemplateLoader(TemplateCache templateCache) {
        this.templateCache = templateCache;
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        int lastUnderscore = name.lastIndexOf('_');
        if (lastUnderscore != -1) {
            name = name.substring(0, lastUnderscore);
        }
        return new DownloadTemplateSource(name, templateCache);
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new InputStreamReader(
                ((DownloadTemplateSource) templateSource).getInputStream(),
                encoding);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }
}
