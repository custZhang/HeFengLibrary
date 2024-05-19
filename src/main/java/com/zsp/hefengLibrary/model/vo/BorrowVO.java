package com.zsp.hefengLibrary.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zsp.hefengLibrary.model.entity.Borrow;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 借阅记录
 * @TableName borrow
 */
@TableName(value ="borrow")
@Data
public class BorrowVO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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

    /**
     * 借书者信息
     */
    private UserVO userVO;

    /**
     * 书本信息
     */
    private BookVO bookVO;

    /**
     * 包装类转对象
     *
     * @param borrowVO
     * @return
     */
    public static Borrow voToObj(BorrowVO borrowVO) {
        if (borrowVO == null) {
            return null;
        }
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO, borrow);
        return borrow;
    }

    /**
     * 对象转包装类
     *
     * @param borrow
     * @return
     */
    public static BorrowVO objToVo(Borrow borrow) {
        if (borrow == null) {
            return null;
        }
        BorrowVO borrowVO = new BorrowVO();
        BeanUtils.copyProperties(borrow, borrowVO);
        return borrowVO;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}