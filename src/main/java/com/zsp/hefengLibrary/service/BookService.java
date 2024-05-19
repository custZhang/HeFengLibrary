package com.zsp.hefengLibrary.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsp.hefengLibrary.model.dto.book.BookQueryRequest;
import com.zsp.hefengLibrary.model.entity.Book;
import com.zsp.hefengLibrary.model.entity.User;
import com.zsp.hefengLibrary.model.vo.BookEditVO;
import com.zsp.hefengLibrary.model.vo.BookVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 */
public interface BookService extends IService<Book> {

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Book> getQueryWrapper(BookQueryRequest questionQueryRequest);

    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    BookVO getBookVO(Book question, HttpServletRequest request);

    /**
     * 获取BookEdit类
     *
     * @param question
     * @param request
     * @return
     */
    BookEditVO getBookEditVO(Book question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<BookVO> getBookVOPage(Page<Book> questionPage, HttpServletRequest request);

}
