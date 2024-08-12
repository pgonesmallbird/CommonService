package org.lgp.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    /**
     * 用户id'
     */

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 性别
     */
    @ExcelProperty("性别")
    private String sex;

    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    private Integer age;

    /**
     * 手机号
     */
    @ExcelProperty("手机号")
    private String phone;

    /**
     * 住址
     */
    @ExcelProperty("住址")
    private String address;
}
