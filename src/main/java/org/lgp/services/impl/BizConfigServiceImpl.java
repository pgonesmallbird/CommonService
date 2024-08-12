package org.lgp.services.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lgp.domain.BizConfigVO;
import org.lgp.domain.entity.BizConfig;
import org.lgp.mapper.BizConfigMapper;
import org.lgp.services.BizConfigService;
import org.lgp.strategy.BizConfigStrategy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BizConfigServiceImpl implements BizConfigService {

    @Resource
    private BizConfigStrategy bizConfigStrategy;

    @Resource
    private BizConfigMapper bizConfigMapper;

    @Override
    public boolean add(BizConfigVO bizConfigVO) {
        log.info("biz service add config.");
        bizConfigStrategy.verifyConfig(bizConfigVO);
        BizConfig bizConfig = BizConfig.builder().key(bizConfigVO.getKey())
                .valueType(bizConfigVO.getValueType())
                .value(bizConfigVO.getValue()).build();
        bizConfigMapper.insert(bizConfig);
        return true;
    }
}
