package be.vinci.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Properties props;

    public static void load(String file) {
        props = new Properties();
        try (InputStream input = new FileInputStream(file)) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static Integer getIntProperty(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    public static boolean getBoolProperty(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }

}
