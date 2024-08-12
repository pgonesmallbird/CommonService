package org.lgp.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.lgp.domain.Achievement;
import org.lgp.domain.User;
import org.lgp.listener.AchievementDataListener;
import org.lgp.listener.UserDataListener;
import org.lgp.services.AchievementService;
import org.lgp.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v1")
@Api(tags = {"数据导出导入"})
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private AchievementService achievementService;

    @ApiOperation(value = "导入单个sheet数据")
    @PostMapping("/importSheet")
    public void importSheet(@RequestParam("multipartFile") MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            //异步读取所有sheet数据,可在sheet方法参数中指定sheet索引,sheet名称
            EasyExcel.read(inputStream, User.class, new UserDataListener(userService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "导入多个sheet数据")
    @PostMapping("/importSheets")
    public void importSheets(@RequestParam("multipartFile") MultipartFile multipartFile) {
        ExcelReader excelReader = null;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            excelReader = EasyExcel.read(inputStream).build();
            ReadSheet readSheet1 =
                    //构建ExcelReader对象
                    EasyExcel.readSheet(0).head(User.class).registerReadListener(new UserDataListener(userService)).build();
            ReadSheet readSheet2 =
                    //构建ExcelReader对象
                    EasyExcel.readSheet(1).head(Achievement.class).registerReadListener(new AchievementDataListener(achievementService)).build();
            //这里注意 一定要把sheet1 sheet2 一起传进去
            excelReader.read(readSheet1, readSheet2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    @ApiOperation(value = "导出数据", produces = "application/octet-stream")
    @PostMapping("/simpleWrite")
    public void simpleWrite(HttpServletResponse response) {
        userService.simpleWrite(response);
    }

    @ApiOperation(value = "分页导出数据", produces = "application/octet-stream")
    @PostMapping("/pageWrite")
    public void pageWrite(HttpServletResponse response) {
        userService.pageWrite(response);
    }

    @ApiOperation(value = "分页导出多sheet数据", produces = "application/octet-stream")
    @PostMapping("/pageWriteSheets")
    public void pageWriteSheets(HttpServletResponse response) {
        userService.pageWriteSheets(response);
    }

    @ApiOperation(value = "分页导出多sheet数据发送邮件")
    @PostMapping("/writeSendMail")
    public void writeSendMail(HttpServletResponse response) throws IOException {
        userService.writeSendMail(response);
    }
}