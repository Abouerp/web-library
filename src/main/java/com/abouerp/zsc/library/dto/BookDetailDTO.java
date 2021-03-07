package com.abouerp.zsc.library.dto;

import com.abouerp.zsc.library.domain.book.BookStatus;
import lombok.Data;

import java.time.Instant;

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
    private Instant returnTime;
    private BookStatus status;
    //借阅次数
    private Integer borrowingTimes;
    //续借次数
    private Integer renewalTimes;
    private String createBy;
    private String updateBy;
}
