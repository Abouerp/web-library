package com.abouerp.zsc.library.dto;

import com.abouerp.zsc.library.domain.Book;
import com.abouerp.zsc.library.domain.BookStatus;
import lombok.Data;

/**
 * @author Abouerp
 */
@Data
public class BookDetailDTO {
    private Integer id;
    //索书号(由对应书籍的code后4位的数字+1)
    private String searchCode;
    //在馆位置
    private String address;
    //还书时间
    private String backTime;
    private BookStatus status;
    //借阅次数
    private Integer borrowingTimes;
    //续借次数
    private Integer renewalTimes;
    private String createBy;
    private String updateBy;
}
