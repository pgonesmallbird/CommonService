package org.lgp.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lgp.domain.BizConfigVO;
import org.lgp.domain.R;
import org.lgp.services.BizConfigService;
import org.lgp.utils.ThreadPoolUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bizConfig")
@Slf4j
public class BizConfigController {

    @Resource
    private BizConfigService bizConfigService;

    @PostMapping("/add")
    public R<Boolean> add(@RequestBody BizConfigVO bizConfigVO) {
        log.info("Biz add config start.");
        ThreadPoolUtils.execute(()-> bizConfigService.add(bizConfigVO));
        return R.ok(null);
    }
}
