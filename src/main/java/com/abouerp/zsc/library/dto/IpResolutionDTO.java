package com.abouerp.zsc.library.dto;

import lombok.Data;
import java.io.Serializable;


/**
 * @author Abouerp
 */
@Data
public class IpResolutionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msg;
    private String code;
    private Result data;

    @Data
    public static class Result implements Serializable {
        private static final long serialVersionUID = 1L;
        private String area;
        //国家
        private String country;
        private String isp_id;
        private String queryIp;
        //市
        private String city;
        private String ip;
        //运营商
        private String isp;
        private String county;
        private String region_id;
        private String area_id;
        private String county_id;
        //省
        private String region;
        private String country_id;
        private String city_id;
    }
}
