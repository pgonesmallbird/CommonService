package org.lgp.loader;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.Resource;
import org.lgp.TemplateCache;
import org.lgp.mapper.FreeMarkRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Component
public class FreeMarkerTemplateEngine implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(FreeMarkerTemplateEngine.class);
    @Resource
    private FreeMarkRecordMapper mapper;

    @Resource
    private TemplateCache templateCache;

    private Configuration cfg;

    public FreeMarkerTemplateEngine() {
    }

    private void initConfiguration() {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.ENGLISH);
        cfg.setNumberFormat("#");
        cfg.setCacheStorage(new NullCacheStorage());
        extendCfg();
    }

    /**
     * 设置template加载方式
     */
    private void extendCfg() {
        TemplateLoader[] loaders = new TemplateLoader[]{new DownloadTemplateLoader(templateCache)};
//        TemplateLoader[] loaders = new TemplateLoader[]{new DbTemplateLoader(mapper)};
        MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
        cfg.setTemplateLoader(mtl);
    }

    public String transform2String(String templateName, Map<String, Object> input) {
        Template template = this.getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        if (Objects.isNull(template)) {
            return "";
        }
        try {
            template.process(input, stringWriter);
        } catch (TemplateException | IOException e) {
            log.error(e.getMessage(), e);
        }
        return stringWriter.toString();
    }

    private Template getTemplate(String templateName) {
        try {
            return cfg.getTemplate(templateName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        this.initConfiguration();
    }


}
