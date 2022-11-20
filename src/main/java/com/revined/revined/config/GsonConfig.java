package com.revined.revined.config;

import com.google.gson.*;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Configuration
public class GsonConfig {
    final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {

        GsonBuilder builder = new GsonBuilder();
        customizers.forEach((c) -> c.customize(builder));

        /**
         * Custom Gson configuration
         */
        builder.registerTypeHierarchyAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                new JsonPrimitive(DATE_TIME_FORMATTER.format(src)));

        builder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString(), DATE_TIME_FORMATTER));

        return builder;
    }
}
