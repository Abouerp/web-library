package com.abouerp.zsc.library.vo;

import com.abouerp.zsc.library.domain.BookStatus;
import lombok.Data;

/**
 * @author Abouerp
 */
@Data
public class BookDetailVO {
    //在馆位置
    private String address;
    private String searchCode;
    //还书时间
    private String backTime;
    private BookStatus status;
    private Integer borrowingTimes = 0;
    private Integer renewalTimes = 0;
    private Integer bookId;
}

