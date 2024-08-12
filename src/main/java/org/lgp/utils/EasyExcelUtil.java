package org.lgp.utils;

import com.alibaba.excel.EasyExcel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.lgp.listener.CustomCellWriteHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class EasyExcelUtil {
    /**
     * 导出数据到excel
     *
     * @param response
     * @param fileName  文件名
     * @param sheetName 表格sheet名
     * @param list      导出数据
     * @param clazz     实体类
     */
    public static void export(HttpServletResponse response, String fileName, String sheetName, List<?> list, Class clazz) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            EasyExcel.write(out, clazz).registerWriteHandler(new CustomCellWriteHandler()).sheet(sheetName).doWrite(list);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
