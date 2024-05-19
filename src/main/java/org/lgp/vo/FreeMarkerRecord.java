package org.lgp.vo;


import lombok.Builder;
import lombok.Getter;


@Builder
@Getter

public class FreeMarkerRecord {
    private String recordCode;
    private Long version;

    private String jsonResource;

}
