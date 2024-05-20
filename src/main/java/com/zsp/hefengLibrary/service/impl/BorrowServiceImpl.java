package com.zsp.hefengLibrary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsp.hefengLibrary.common.ErrorCode;
import com.zsp.hefengLibrary.constant.CommonConstant;
import com.zsp.hefengLibrary.exception.BusinessException;
import com.zsp.hefengLibrary.mapper.BorrowMapper;
import com.zsp.hefengLibrary.model.dto.borrow.BorrowAddRequest;
import com.zsp.hefengLibrary.model.dto.borrow.BorrowQueryRequest;
import com.zsp.hefengLibrary.model.entity.Book;
import com.zsp.hefengLibrary.model.entity.Borrow;
import com.zsp.hefengLibrary.model.entity.User;
import com.zsp.hefengLibrary.model.vo.BookVO;
import com.zsp.hefengLibrary.model.vo.BorrowVO;
import com.zsp.hefengLibrary.model.vo.UserVO;
import com.zsp.hefengLibrary.service.BorrowService;
import com.zsp.hefengLibrary.service.UserService;
import com.zsp.hefengLibrary.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.zsp.hefengLibrary.service.BookService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author j
 * @description 针对表【book_submit(题目提交)】的数据库操作Service实现
 * @createDate 2023-11-24 16:56:03
 */
@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow>
        implements BorrowService {
    @Resource
    private BookService bookService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param
     * @param loginUser
     * @return
     */
    @Override
    public long doBorrow(BorrowAddRequest borrowAddRequest, User loginUser) {
        long bookId = borrowAddRequest.getBookId();
        // 判断实体是否存在，根据类别获取实体
        Book book = bookService.getById(bookId);
        if (book == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 锁必须要包裹住事务方法
        Borrow borrow = new Borrow();
        borrow.setUserId(userId);
        borrow.setBookId(bookId);
        borrow.setIsReturned(0);
        //保存到数据库
        boolean save = this.save(borrow);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        //执行判题服务
        Long borrowId = borrow.getId();
        return borrowId;
    }

    /**
     * 获取查询包装类(用户根据了哪些字段查询，根据前端传来的请求对象，得到mybatis框架支持的查询QueryWrapper类)
     *
     * @param borrowQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Borrow> getQueryWrapper(BorrowQueryRequest borrowQueryRequest) {
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        if (borrowQueryRequest == null) {
            return queryWrapper;
        }
        Long userId = borrowQueryRequest.getUserId();
        Long bookId = borrowQueryRequest.getBookId();
        Date borrowDate = borrowQueryRequest.getBorrowDate();
        Date returnDate = borrowQueryRequest.getReturnDate();
        Integer isReturned = borrowQueryRequest.getIsReturned();
        String sortField = borrowQueryRequest.getSortField();
        String sortOrder = borrowQueryRequest.getSortOrder();

        boolean userIdNotNull = (ObjectUtils.isNotEmpty(userId) && userId != 0);
        boolean bookIdNotNull = (ObjectUtils.isNotEmpty(bookId) && bookId != 0);

        // 拼接查询条件
        queryWrapper.eq(userIdNotNull, "userId", userId);
        queryWrapper.eq(bookIdNotNull, "bookId", bookId);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(borrowDate), "borrowDate", borrowDate);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(returnDate), "returnDate", returnDate);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(isReturned), "isReturned", isReturned);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Lazy
    private Map<Long, UserVO> userVOMap = new HashMap<>();

    @Lazy
    private Map<Long, BookVO> bookVOMap = new HashMap<>();


    @Override
    public BorrowVO getBorrowVO(Borrow borrow, User loginUser) {
        BorrowVO borrowVO = BorrowVO.objToVo(borrow);
//        long loginUserId = loginUser.getId();
//        if (loginUserId != borrow.getUserId() && userService.isAdmin(loginUser)) {//如果不是提交者，也不是管理员的话
//            borrowVO.setCode(null);
//        }
        //往BorrowVO里设置userVO
        Long userId = borrow.getUserId();
        UserVO userVO = userVOMap.get(userId);//先查询map里有没有，没有再去数据库获取
        if(userVO == null){
            User bookUser = userService.getById(userId);//获取当前borrow的user对象
            userVO = userService.getUserVO(bookUser);//转成uservo对象
            userVOMap.put(userId,userVO);//首次查询后，放进去
        }
        borrowVO.setUserVO(userVO);
        //往BorrowVO里设置BookVO
        Long bookId = borrowVO.getBookId();
        BookVO bookVO = bookVOMap.get(bookId);
        if(bookVO == null){
            Book book = bookService.getById(bookId);//获取当前book对象
            bookVO = BookVO.objToVo(book);
            bookVOMap.put(bookId,bookVO);//首次查询后，放进去
        }
        borrowVO.setBookVO(bookVO);
        return borrowVO;
    }

    @Override
    public Page<BorrowVO> getBorrowVOPage(Page<Borrow> borrowPage, User loginUser) {
        List<Borrow> borrowList = borrowPage.getRecords();
        Page<BorrowVO> borrowVOPage = new Page<>(borrowPage.getCurrent(), borrowPage.getSize(), borrowPage.getTotal());
        if (CollectionUtils.isEmpty(borrowList)) {
            return borrowVOPage;
        }
        List<BorrowVO> borrowVOList = borrowList.stream()
                .map(borrow -> getBorrowVO(borrow, loginUser))
                .collect(Collectors.toList());
        borrowVOPage.setRecords(borrowVOList);
        return borrowVOPage;
    }


}




