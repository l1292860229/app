package com.example.administrator.util;

import java.io.File;

/**
 * Created by Administrator on 2017/3/27.
 */

public class FileUtil {
    public static boolean newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
