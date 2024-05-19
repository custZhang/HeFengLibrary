package com.zsp.hefengLibrary.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zsp.hefengLibrary.model.entity.Book;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 图书
 * @TableName book
 */
@TableName(value ="book")
@Data
public class BookEditVO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 书名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * ISBN编号
     */
    private String isbn;

    /**
     * 出版日期
     */
    private Date publishDate;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer quantity;

    /**
     * 分类
     */
    private String category;

    /**
     * 包装类转对象
     *
     * @param BookVO
     * @return
     */
    public static Book voToObj(BookEditVO BookVO) {
        if (BookVO == null) {
            return null;
        }
        Book book = new Book();
        BeanUtils.copyProperties(BookVO, book);
        return book;
    }

    /**
     * 对象转包装类
     *
     * @param Book
     * @return
     */
    public static BookEditVO objToVo(Book Book) {
        if (Book == null) {
            return null;
        }
        BookEditVO bookVO = new BookEditVO();
        BeanUtils.copyProperties(Book, bookVO);
        return bookVO;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}