package com.onlinestore.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class PatternCustom {
    public String registrationError(String error){
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)=\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(error);
        StringBuilder result = new StringBuilder();

        if (matcher.find()) {
            String field = matcher.group(1); // Первые круглые скобки
            String value = matcher.group(2); // Вторые круглые скобки

            result.append(field).append(" ").append(value).append(" уже существует");
            log.info(result.toString());
            return result.toString();
        } else {
            return "";
        }
    }
}
