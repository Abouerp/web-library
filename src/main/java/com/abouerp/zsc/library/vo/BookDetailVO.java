package com.abouerp.zsc.library.vo;

import com.abouerp.zsc.library.domain.book.BookStatus;
import lombok.Data;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
public class BookDetailVO {
    //在馆位置
    private String address;
    private String searchCode;
    //还书时间
    private Instant returnTime;
    private BookStatus status;
    private Integer borrowingTimes = 0;
    private Integer renewalTimes = 0;
    private Integer bookId;
}

