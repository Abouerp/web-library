package com.abouerp.zsc.library.utils;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Abouerp
 */
@SpringBootTest
public class RandomDateUtilsTest {

    @Test
    public void dateTime(){
        System.out.println(RandomDateUtils.dateTime());
    }
}