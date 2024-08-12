package org.lgp.services.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.lgp.domain.User;
import org.lgp.mapper.UserMapper;
import org.lgp.services.UserService;
import org.lgp.utils.DeleteFileUtil;
import org.lgp.utils.EasyExcelUtil;
import org.lgp.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获得程序当前路径
     */
    private String basePath = System.getProperty("user.dir");

    @Override
    public void insertBatch(List<User> users) {
        userMapper.insertBatch(users);
    }

    @Override
    public void simpleWrite(HttpServletResponse response) {
        List<User> all = userMapper.findAll();
        System.out.println(JSON.toJSONString(all));
        EasyExcelUtil.export(response, "test01", "sheet1", userMapper.findAll(), User.class);
    }

    @Override
    public void pageWrite(HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try (ServletOutputStream out = response.getOutputStream()) {
            //设置字符集为utf-8
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //通知浏览器服务器发送的数据格式
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("Test02" + ".xlsx", "UTF-8"));
            //发送一个报头，告诉浏览器当前页面不进行缓存，每次访问的时间必须从服务器上读取最新的数据
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(out, User.class).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet").build();
            Integer number = userMapper.number();
            int page = (int) Math.ceil((double) number / (double) 10);
            // 去调用写入,根据数据库分页的总的页数来
            for (int i = 1; i <= page; i++) {
                //先定义一个空集合每次循环使他变成null减少内存的占用
                List<User> pageUser = null;
                Page<User> pageObj = PageHelper.startPage(i, 10);
                pageUser = userMapper.findPageUser(pageObj);
                excelWriter.write(pageUser, writeSheet);
                pageUser.clear();
            }
            // 千万别忘记finish 会帮忙关闭流
            excelWriter.finish();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void pageWriteSheets(HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("Test03" + ".xlsx", "UTF-8"));
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(out, User.class).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet").build();
            Integer number = userMapper.number();
            Integer count = 0;
            int page = (int) Math.ceil((double) number / (double) 10);
            // 去调用写入,根据数据库分页的总的页数来
            for (int i = 1; i <= page; i++) {
                if (count == 2) {
                    writeSheet = EasyExcel.writerSheet("sheet2").build();
                    count = 0;
                }
                //先定义一个空集合每次循环使他变成null减少内存的占用
                List<User> pageUser = null;
                Page<User> pageObj = PageHelper.startPage(i, 10);
                pageUser = userMapper.findPageUser(pageObj);
                excelWriter.write(pageUser, writeSheet);
                pageUser.clear();
                count++;
            }
            // 千万别忘记finish 会帮忙关闭流
            excelWriter.finish();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void writeSendMail(HttpServletResponse response) throws IOException {
        ExcelWriter excelWriter = null;
        UUID uuid = UUID.randomUUID();
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(basePath + "\\src\\main\\resources\\file\\" + uuid + ".zip"));
        ZipEntry entry = new ZipEntry("明细查询.xlsx");
        zip.putNextEntry(entry);
        try {
            // 这里需要指定写用哪个class去写
            excelWriter = EasyExcel.write(zip, User.class).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet").build();
            Integer number = userMapper.number();
            int count = 0;
            int page = (int) Math.ceil((double) number / (double) 10);
            // 去调用写入,根据数据库分页的总的页数来
            for (int i = 1; i <= page; i++) {
                if (count == 2) {
                    writeSheet = EasyExcel.writerSheet("sheet2").build();
                    count = 0;
                }
                //先定义一个空集合每次循环使他变成null减少内存的占用
                List<User> pageUser = null;
                Page<User> pageObj = PageHelper.startPage(i, 10);
                pageUser = userMapper.findPageUser(pageObj);
                excelWriter.write(pageUser, writeSheet);
                pageUser.clear();
                count++;
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
                zip.flush();
                zip.close();
            }
        }
        List<String> receiveList = new ArrayList<>();
        receiveList.add("2209273361@qq.com");
        EmailUtil.sendEmail("明细查询", "查询数据已发送,请注意查收！", receiveList, basePath + "\\src\\main\\resources\\file\\" + uuid + ".zip");
        DeleteFileUtil.delete(basePath + "\\src\\main\\resources\\file\\" + uuid + ".zip");
    }
}
