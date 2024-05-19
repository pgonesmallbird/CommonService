package org.lgp.mapper;

import org.lgp.vo.FreeMarkerRecord;
import org.springframework.stereotype.Component;

@Component
public class FreeMarkRecordMapper {

    public FreeMarkerRecord selectOne(FreeMarkerRecord record) {
        return FreeMarkerRecord.builder().recordCode("test").version(1L).jsonResource("""
                <cad>
                    <doclist name="A.ASM" stone="${phone}">
                		<doc name="B.ASM" stone="${B_ASM}" >HELLO</doc>
                		<doc name="C.ASM" stone="${C_ASM}" >HELLO</doc>	
                	</doclist>
                	<doclist name="A.ASM" stone="${A_ASM}">
                		<doc name="B.ASM" stone="${B_ASM}" >HELLO</doc>
                		<doc name="C.ASM" stone="${C_ASM}" >HELLO</doc>	
                	</doclist>
                	<doclist name="B.ASM" stone="${B_ASM}">
                		<doc name="C.ASM" stone="${C_ASM}">HELLO</doc>
                	</doclist>
                </cad>
                                
                """).build();
    }
}
