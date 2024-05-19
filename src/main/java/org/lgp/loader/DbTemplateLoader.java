package org.lgp.loader;

import freemarker.cache.TemplateLoader;
import org.lgp.mapper.FreeMarkRecordMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class DbTemplateLoader implements TemplateLoader {
    private FreeMarkRecordMapper mapper;

    DbTemplateLoader(FreeMarkRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object findTemplateSource(String name) {
        int lastUnderscore = name.lastIndexOf('_');
        if (lastUnderscore != -1) {
            name = name.substring(0, lastUnderscore);
        }
        return new DbTemplateSource(mapper, name);
    }

    /**
     * 最后修改的版本，用于判断是否直接读取缓存
     * @param templateSource
     * @return
     */
    @Override
    public long getLastModified(Object templateSource) {
        return ((DbTemplateSource) templateSource).lastModified();
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new InputStreamReader(
                ((DbTemplateSource) templateSource).getInputStream(),
                encoding);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        ((DbTemplateSource) templateSource).close();
    }
}


