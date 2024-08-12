package org.lgp.strategy;

import org.lgp.domain.BizConfigVO;

public interface BizConfigStrategy {

    /**
     * KEY类型
     *
     * @return 类型
     */
    String configType();

    /**
     * 校验配置
     *
     * @param bizConfigVO 配置信息
     */
    void verifyConfig(BizConfigVO bizConfigVO);


}
