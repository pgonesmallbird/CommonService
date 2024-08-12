package org.lgp.mapper;

import com.github.pagehelper.Page;
import org.lgp.domain.User;

import java.util.List;

public interface UserMapper {

    void insertBatch(List<User> users);
    List<User> findPageUser(Page<User> pageObj);

    int number();

    List<User> findAll();
}
