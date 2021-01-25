package com.abouerp.zsc.library.config;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;


/**
 * @author Abouerp
 */
@Configuration
public class ElasticsearchConfiguration {

    @Bean
    RestHighLevelClient client() {
        //todo 后期待修改连接
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("elastic:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}

