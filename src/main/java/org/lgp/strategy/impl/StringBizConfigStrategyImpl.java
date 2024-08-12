package org.lgp.strategy.impl;

import io.micrometer.common.util.StringUtils;
import org.lgp.domain.BizConfigVO;
import org.lgp.strategy.BizConfigStrategy;
import org.springframework.stereotype.Service;

@Service
public class StringBizConfigStrategyImpl implements BizConfigStrategy {
    @Override
    public String configType() {
        return "String";
    }

    @Override
    public void verifyConfig(BizConfigVO bizConfigVO) {
        if (StringUtils.isBlank(bizConfigVO.getKey())) {
            throw new RuntimeException("配置KEY为空");
        }
    }
}
