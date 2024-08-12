package org.lgp.listener;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

@Slf4j
public class CustomCellWriteHandler extends AbstractCellWriteHandler {
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        log.info("第 [{},{}] 写入完成。", cell.getRowIndex(), cell.getColumnIndex());
        System.out.println(JSON.toJSONString(cellDataList));
        System.out.println(cellDataList.size());
        super.afterCellDispose(writeSheetHolder, writeTableHolder, cellDataList, cell, head, relativeRowIndex, isHead);
    }
}
