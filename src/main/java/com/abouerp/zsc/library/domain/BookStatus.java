package com.abouerp.zsc.library.domain;

/**
 * @author Abouerp
 */
public enum BookStatus {
    //已借
    OUTLIBRARY,
    //在馆
    INLIBRARY,
    //丢失
    LOST,
    //逾期
    OVERDUE,
    //预约中
    SUBSCRIBE;
}
