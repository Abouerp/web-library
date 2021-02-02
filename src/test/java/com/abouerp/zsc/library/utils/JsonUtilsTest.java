package com.abouerp.zsc.library.utils;


import com.abouerp.zsc.library.domain.book.Book;
import com.abouerp.zsc.library.domain.book.BookCategory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.regex.Pattern;

/**
 * @author Abouerp
 */
@SpringBootTest
public class JsonUtilsTest {

    @Test
    public void test1() {
        Book book = new Book().setAuthor("哈哈")
                .setCode("xx")
                .setDescription("sss")
                .setIsbn("dssd")
                .setName("springboot")
                .setPublicationTime("12.47.5")
                .setId(1)
                .setBookCategory(new BookCategory().setCode("21").setName("hss").setId(1))
                .setCreateTime(Instant.now());
        String str = JsonUtils.writeValueAsString(book);
        System.out.println(str);
        Book book1 = JsonUtils.readValue(str, Book.class);
        System.out.println(book1);
    }

    @Test
    public void test2() {
        String regx = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        System.out.println(Pattern.compile(regx).matcher("18320581956").matches());
    }
}