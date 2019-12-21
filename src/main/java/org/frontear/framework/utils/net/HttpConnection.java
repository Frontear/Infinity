package org.frontear.framework.utils.net;

import java.io.*;
import java.net.*;
import lombok.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpConnection {
    public String get(@NonNull final String url) {
        val connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        val string = new StringBuilder();
        val stream = connection.getInputStream();

        try (val reader = new BufferedReader(new InputStreamReader(stream))) {
            reader.forEachLine(string::append);
        }

        return string.toString();
    }
}
