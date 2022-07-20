package dev.thalia.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomClassLoader extends ClassLoader {

    @Override
    public Class findClass(String name, String url) {
        byte[] b = new byte[0];
        try {
            b = loadClassFromURL(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromURL(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        InputStream inputStream = connection.getInputStream();
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        inputStream.close();
        return buffer;
    }

}
