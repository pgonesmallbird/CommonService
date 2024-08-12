package org.lgp.services;

import jakarta.servlet.http.HttpServletResponse;
import org.lgp.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    /**
     * 批量插入
     *
     * @param users
     */
    void insertBatch(List<User> users);

    /**
     * 导出数据
     *
     * @param response
     */
    void simpleWrite(HttpServletResponse response);

    /**
     * 分页导出数据
     *
     * @param response
     */
    void pageWrite(HttpServletResponse response);

    /**
     * 分页多sheet导出数据
     *
     * @param response
     */
    void pageWriteSheets(HttpServletResponse response);

    /**
     * 发送excel到邮箱
     *
     * @param response
     */
    void writeSendMail(HttpServletResponse response) throws IOException;

}
