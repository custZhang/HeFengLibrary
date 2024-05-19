package com.zsp.hefengLibrary.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsp.hefengLibrary.model.dto.borrow.BorrowAddRequest;
import com.zsp.hefengLibrary.model.dto.borrow.BorrowQueryRequest;
import com.zsp.hefengLibrary.model.entity.Borrow;
import com.zsp.hefengLibrary.model.entity.User;
import com.zsp.hefengLibrary.model.vo.BorrowVO;

/**
* @author j
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-11-24 16:56:03
*/
public interface BorrowService extends IService<Borrow> {

    /**
     * 题目提交
     *
     * @param borrowAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doBorrow(BorrowAddRequest borrowAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param borrowQueryRequest
     * @return
     */
    QueryWrapper<Borrow> getQueryWrapper(BorrowQueryRequest borrowQueryRequest);


    /**
     * 获取题目封装
     *
     * @param borrow
     * @param loginUser
     * @return
     */
    BorrowVO getBorrowVO(Borrow borrow, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param borrowPage
     * @param loginUser
     * @return
     */
    Page<BorrowVO> getBorrowVOPage(Page<Borrow> borrowPage, User loginUser);

}
