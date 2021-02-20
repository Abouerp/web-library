package com.abouerp.zsc.library.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 深圳图书馆的接口返回数据
 * @author Abouerp
 */
@Data
public class ReptileBookDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Message data;

    @Data
    public static class Message implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer numFound;
        private List<Result> docs;
    }

    @Data
    public static class Result implements Serializable {
        private static final long serialVersionUID = 1L;
        private String publisher;
        private List<String> all;
        private String u_publish;
        private String u_isbn;
        private String author;
        private List<String> f_author;
        private String u_cover;
        private String callno;
        private String isbn;
        private String u_page;
        private String cover_path;
        private String id;
        private String edition;
        private String u_title;
        private String local_reserve;
        private String u_price;
        private String tablename;
        private String library;
        private String local_children;
        private String publishyear;
        private String ptitle;
        private String recordid;
        private String serviceaddr;
        private String cirtype_l;
        private String classno;
        private String subject;
        private List<String> f_subject;
        private String title;
        private String u_abstract;
        private String _version_;

    }
}
