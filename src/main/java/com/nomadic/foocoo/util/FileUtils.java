package com.nomadic.foocoo.util;

import org.jsoup.nodes.Document;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by peaches on 2015-08-22.
 */
public class FileUtils {

    public static void saveDocToFileSystem(Document doc, String folderPath, String urlSource) {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(getFile(folderPath, urlSource));
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

            FileOutputStream fileOutputStream = new FileOutputStream(getFile(folderPath, urlSource));
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

    public static File getFile(String folderPath, String urlSource) throws MalformedURLException {
        URL url = new URL(urlSource);
        String fileName = url.getPath();
        if (fileName.indexOf('?') > 0) {
            fileName = fileName.substring(0, fileName.indexOf('?'));
        }
        String filePath = "";
        if (fileName.lastIndexOf('/') >= 0) {
            filePath = fileName.substring(0, fileName.lastIndexOf('/'));
            fileName = fileName.substring(fileName.lastIndexOf('/'));
        }
        if (fileName.length() == 0 || fileName.equals("/")) {
            fileName = "/index.htm";
        }
        File file = new File(folderPath + url.getHost() + filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getPath() + fileName);
    }
}
