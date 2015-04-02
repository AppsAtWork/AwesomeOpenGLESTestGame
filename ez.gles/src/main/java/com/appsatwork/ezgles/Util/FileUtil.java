package com.appsatwork.ezgles.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Broodrooster on 2-4-2015.
 */
public class FileUtil
{
    public static String ReadStringFromRawPath(String path) throws Exception
    {
        InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line);
        reader.close();
        return sb.toString();
    }

}
