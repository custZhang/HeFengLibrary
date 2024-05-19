package com.zsp.hefengLibrary.model.dto.borrow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zsp.hefengLibrary.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 借阅记录
 * @TableName borrow
 */
@TableName(value ="borrow")
@Data
public class BorrowQueryRequest extends PageRequest implements Serializable{
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 读者ID
     */
    private Long userId;

    /**
     * 图书ID
     */
    private Long bookId;

    /**
     * 借出日期
     */
    private Date borrowDate;

    /**
     * 归还日期
     */
    private Date returnDate;

    /**
     * 是否归还
     */
    private Integer isReturned;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}