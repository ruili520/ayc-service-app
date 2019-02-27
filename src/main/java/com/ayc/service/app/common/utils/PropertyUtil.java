package com.ayc.service.app.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private PropertyUtil(){}

    private static Properties loveAycProperties;

    public static Properties getInstance(){
        if (loveAycProperties == null) {
            loveAycProperties = new Properties();
            InputStream in = null;
            try {
                in = PropertyUtil.class.getResourceAsStream("/pay-loveAyc.properties");
                loveAycProperties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try { in.close(); }
                catch (IOException e) { e.printStackTrace();
                }
            }
        }

        return loveAycProperties;
    }
}
