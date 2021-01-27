package com.abouerp.zsc.library.utils;


import com.abouerp.zsc.library.dto.IpResolutionDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class IpResolutionUtilTest {

    @Test
    public void test1() {
        IpResolutionDTO ipResolutionDTO = IpResolutionUtils.resolution("113.100.28.123");
        System.out.println(ipResolutionDTO);
    }
}