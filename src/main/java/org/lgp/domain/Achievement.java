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
@TableName("achievement")
public class Achievement {
    /**
     * id'
     */

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @ExcelProperty("用户id")
    @TableId(value = "uid")
    private Integer uid;
    /**
     * 语文
     */
    @ExcelProperty("语文")
    private Double chinese;
    /**
     * 数学
     */
    @ExcelProperty("数学")
    private Double mathematics;
    /**
     * 英语
     */
    @ExcelProperty("英语")
    private Double english;
    /**
     * 总和
     */
    @ExcelProperty("总和")
    private Double sum;
}
