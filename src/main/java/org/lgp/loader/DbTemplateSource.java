package org.lgp.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.lgp.mapper.FreeMarkRecordMapper;
import org.lgp.vo.FreeMarkerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class DbTemplateSource {
    private FreeMarkRecordMapper mapper;
    private FreeMarkerRecord freeMarkerRecord;
    private InputStream inputStream;

    DbTemplateSource(FreeMarkRecordMapper mapper, String name) {
        this.mapper = mapper;
        this.freeMarkerRecord = FreeMarkerRecord.builder().recordCode(name).build();
    }

    long lastModified() {
        if (Objects.isNull(mapper)) {
            return 0;
        }
        //优化建议：可以设置缓存，库表修改后更新缓存
        FreeMarkerRecord freeMarkerRecordTemp = mapper.selectOne(freeMarkerRecord);
        if (Objects.isNull(freeMarkerRecordTemp)) {
            return 0;
        }
        return freeMarkerRecordTemp.getVersion();
    }

    InputStream getInputStream() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.info("异常={}", e);
            }
        }
        if (Objects.isNull(mapper)) {
            return IOUtils.toInputStream(
                    new String("not found resource."), StandardCharsets.UTF_8.name());
        }
        FreeMarkerRecord freeMarkerRecordTemp = mapper.selectOne(freeMarkerRecord);
        String jsonData = "";
        if (Objects.nonNull(freeMarkerRecordTemp)) {
            jsonData = freeMarkerRecordTemp.getJsonResource();
        }
        inputStream = IOUtils.toInputStream(jsonData, StandardCharsets.UTF_8.name());
        return inputStream;
    }

    void close() throws IOException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } finally {
            inputStream = null;
        }
    }

    /**
     * 比较两个templateSource是否相同，用于判断是否直接读取缓存
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof DbTemplateSource) {
            return Objects.equals(freeMarkerRecord.getRecordCode(), ((DbTemplateSource) o).getFreeMarkerRecord().getRecordCode());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return freeMarkerRecord.getRecordCode().hashCode();
    }


    private FreeMarkerRecord getFreeMarkerRecord() {
        return freeMarkerRecord;
    }

}
