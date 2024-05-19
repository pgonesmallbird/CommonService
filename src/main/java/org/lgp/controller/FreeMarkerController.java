package org.lgp.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lgp.TemplateCache;
import org.lgp.loader.FreeMarkerTemplateEngine;
import org.lgp.vo.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FreeMarkerController {

    @Resource
    private FreeMarkerTemplateEngine templateEngine;

    @Resource
    private TemplateCache templateCache;

    @GetMapping("/transform2String")
    public String transform2String(@RequestParam(name = "templateName") String templateName) {
        HashMap<String, Object> input = Maps.newHashMap();
        input.put("A_ASM", "ZHANGSAN");
        input.put("B_ASM", "LISI");
        input.put("C_ASM", "WANGWU");
        input.put("phone", "15575511111");
        String result = templateEngine.transform2String(templateName, input);
        log.warn("result:{}", result);
        return result;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file, @RequestParam("jsonStr") String jsonStr) throws IOException {
        if (file == null) {
            return Constants.FAILED;
        }
        String originalFilename = file.getOriginalFilename();
        log.info("upload originalFilename:{}", originalFilename);
        log.info("upload file size:{}", file.getSize());
        log.info("upload jsonStr:{}", jsonStr);
        InputStream inputStream = file.getInputStream();
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//        int start = -1;
//        byte[] buffer = new byte[1024];
//        while ((start = bufferedInputStream.read(buffer)) != -1) {
//            log.info(new String(buffer,0,start));
//        }
//        bufferedInputStream.close();
//        log.info("upload file end.");
        String templateName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        templateName = templateName.replaceAll("\\.","_");
        templateCache.put(templateName, inputStream);

        Map map = JSONObject.parseObject(jsonStr, Map.class);
        String result = templateEngine.transform2String(templateName, map);
        log.warn("result:{}", result);
        return result;
    }

}
