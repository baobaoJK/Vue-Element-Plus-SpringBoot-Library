package com.ksamar.library.repository;

import com.ksamar.library.entitys.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 图书数据库操作接口
 * @author KSamer
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * 书名模糊查询
     * @param name 书名
     * @param pageable 分页
     * @return 图书列表
     */
    @Query(value = "select book from Book book where book.name like %?1%")
    Page<Book> findByNameLike(String name, Pageable pageable);

    /**
     * 作者模糊查询
     * @param author 作者
     * @param pageable 分页
     * @return 图书列表
     */
    @Query(value = "select book from Book book where book.author like %?1%")
    Page<Book> findByAuthorLike(String author, Pageable pageable);

    /**
     * 出版社模糊查询
     * @param press 出版社
     * @param pageable 分页
     * @return 图书列表
     */
    @Query(value = "select book from Book book where book.press like %?1%")
    Page<Book> findByPressLike(String press, Pageable pageable);

    /**
     * ISBN 号码模糊查询
     * @param isbn ISBN号码
     * @param pageable 分页
     * @return 图书列表
     */
    @Query(value = "select book from Book book where book.isbn like %?1%")
    Page<Book> findByIsbnLike(String isbn, Pageable pageable);

    /**
     * ISBN 号码查询
     * @param isbn ISBN号码
     * @return 图书信息
     */
    Book findByIsbn(String isbn);
}
