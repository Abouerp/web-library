package com.abouerp.zsc.library.utils;

import com.abouerp.zsc.library.dto.IpResolutionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Abouerp
 */
@Slf4j
public abstract class IpResolutionUtils {
    private static final String ROOT_PATH = "http://ip.taobao.com/outGetIpInfo";
    private static final String TOKEN_KEY = "accessKey";
    private static final String TOKEN_VALUE = "alibaba-inc";

    private static volatile WebClient webClient;

    private IpResolutionUtils() {
    }

    private static WebClient getInstance() {
        if (webClient == null) {
            synchronized (WebClient.class) {
                if (webClient == null) {
                    webClient = WebClient.create();
                }
            }
        }
        return webClient;
    }


    public static IpResolutionDTO resolution(String ip) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(ROOT_PATH)
                .queryParam(TOKEN_KEY, TOKEN_VALUE)
                .queryParam("ip", ip);
//        log.info(uriComponentsBuilder.toUriString());
        return getInstance().get()
                .uri(uriComponentsBuilder.toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .map(it -> JsonUtils.readValue(it, IpResolutionDTO.class))
                .filter(it -> it.getData() != null)
                .block();
    }

}
