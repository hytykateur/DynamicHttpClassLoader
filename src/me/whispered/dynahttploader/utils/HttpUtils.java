package me.whispered.dynahttploader.utils;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    public static URL createUrlFromString(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] getUrlToBytes(URL url) throws IOException {
        return IOUtils.readAllBytes(getUrlToInputStream(url));
    }
    public static InputStream getUrlToInputStream(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }
}
