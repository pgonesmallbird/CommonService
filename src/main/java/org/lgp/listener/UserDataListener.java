package org.lgp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.lgp.domain.User;
import org.lgp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDataListener extends AnalysisEventListener<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataListener.class);
    /**
     * 每隔3000条存储数据库,然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<User> list = new ArrayList<>();

    private UserService userService;


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param userService
     */
    public UserDataListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void invoke(User data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        userService.insertBatch(list);
        LOGGER.info("存储数据库成功！");
    }
}