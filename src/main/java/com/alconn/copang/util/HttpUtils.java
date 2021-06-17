package com.alconn.copang.util;

import com.alconn.copang.enums.Headers;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class HttpUtils {

    public static Optional<String> getHeader(HttpServletRequest request, String key) {
        Map<String ,String> header = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader));
        String val = header.get(key);
        log.debug("k ; {}, val {}", key, header.get(key));
        if (val != null) {
            return Optional.ofNullable(val.split(" ")[1]);
        }
        return Optional.ofNullable(header.get(key));
    }

    public static Optional<String> getHeader(HttpServletRequest request, Headers header){
        return getHeader(request, header.val());
    }


}
