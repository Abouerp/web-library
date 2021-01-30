package com.abouerp.zsc.library.config;


import com.abouerp.zsc.library.utils.JsonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Abouerp
 */
@Configuration
public class ElasticsearchConfiguration {

    @Bean
    @Primary
    RestHighLevelClient client(ElasticSearchProperties elasticSearchProperties) {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticSearchProperties.getUrl())
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    /**
     * 解决关于时间戳格式转换错误问题
     * @return
     */
    @Bean
    public EntityMapper getEntityMapper() {
        return new CustomEntityMapper();
    }

    public static class CustomEntityMapper implements EntityMapper {

        public CustomEntityMapper() {
            ObjectMapper objectMapper = JsonUtils.DEFAULT_MAPPER;
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        }

        @Override
        public String mapToString(Object object) {
            return JsonUtils.writeValueAsString(object);
        }

        @Override
        public <T> T mapToObject(String source, Class<T> clazz) {
            return JsonUtils.readValue(source, clazz);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Map<String, Object> mapObject(Object source) {
            return JsonUtils.readValue(mapToString(source), HashMap.class);
        }

        @Override
        public <T> T readObject(Map<String, Object> source, Class<T> targetType) {
            return mapToObject(mapToString(source), targetType);
        }
    }

    @Bean
    ElasticsearchOperations elasticsearchTemplate(ElasticsearchConverter elasticsearchConverter, ElasticSearchProperties elasticSearchProperties){
        return new ElasticsearchRestTemplate(client(elasticSearchProperties),elasticsearchConverter,getEntityMapper());
    }
}

