package com.nomadic.foocoo.util;

import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;

/**
 * Created by peaches on 2015-08-22.
 */
public class FileUtils {

    public static void saveDocToFileSystem(Document doc, String folderPath, String urlSource) {
        try {
            URL url = new URL(urlSource);
            String fileName = url.getPath();
            if (fileName.indexOf('?') > 0) {
                fileName = fileName.substring(0, fileName.indexOf('?'));
            }
            String filePath = fileName.substring(0, fileName.lastIndexOf('/'));
            fileName = fileName.substring(fileName.lastIndexOf('/'));
            if (fileName.length() == 0 || fileName.equals("/")) {
                fileName = "/index.htm";
            }
            File file = new File(folderPath + url.getHost() + filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getPath() + fileName));
            fileOutputStream.write(doc.toString().getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStreamToFileSystem(InputStream doc, String folderPath, String urlSource) {
        try {
            URL url = new URL(urlSource);
            String fileName = url.getPath();
            if (fileName.indexOf('?') > 0) {
                fileName = fileName.substring(0, fileName.indexOf('?'));
            }
            String filePath = fileName.substring(0, fileName.lastIndexOf('/'));
            fileName = fileName.substring(fileName.lastIndexOf('/'));
            if (fileName.length() == 0 || fileName.equals("/")) {
                fileName = "/index.htm";
            }
            File file = new File(folderPath + url.getHost() + filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getPath() + fileName));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = doc.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            doc.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
