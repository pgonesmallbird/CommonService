package org.lgp.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
public class BizConfig {
    private Long id;
    private String key;
    private String valueType;
    private String value;
}
