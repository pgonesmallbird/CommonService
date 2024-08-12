package org.lgp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lgp.domain.entity.BizConfig;

@Mapper
public interface BizConfigMapper {

    int insert(BizConfig bizConfig);
}
