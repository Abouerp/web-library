package com.abouerp.zsc.library.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author Abouerp
 */
@Slf4j
public abstract class JsonUtils {
    public static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper().findAndRegisterModules();

    @Nullable
    public static String writeValueAsString(@Nullable Object object) {
        try {
            return DEFAULT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Nullable
    public static <T> T readValue(@Nullable String str, @NonNull Class<T> aClass) {
        try {
            log.info(str);
            return DEFAULT_MAPPER.readValue(str, aClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
