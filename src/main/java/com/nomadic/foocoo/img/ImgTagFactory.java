package com.nomadic.foocoo.img;

import com.nomadic.foocoo.core.UrlQueueFactory;
import com.nomadic.foocoo.html.HtmlContent;
import com.nomadic.foocoo.util.FileUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * html img 标签工厂，
 * Created by peaches on 2015-08-22.
 */
public class ImgTagFactory implements Runnable {
    public static int priority = 6;

    private static UrlQueueFactory urlQueueFactory = new UrlQueueFactory();

    public static UrlQueueFactory getUrlQueueFactory() {
        return urlQueueFactory;
    }

    public static void getImage(String url) {

        try {
            Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            String url = getUrlQueueFactory().getNextUrl();
            if (url == null) {
                try {
                    Thread.sleep(3000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("处理 img 标签url:" + url);
            try {
                URL imgUrl = new URL(url);
                // 打开链接
                HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
                // 设置请求方式为"GET"
                conn.setRequestMethod("GET");
                // 超时响应时间为5秒
                conn.setConnectTimeout(5 * 1000);

                FileUtils.saveStreamToFileSystem(conn.getInputStream(), HtmlContent.folderPath, url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getUrlQueueFactory().handlerComplete(url);
            System.out.println(" img 标签处理完成url:" + url);
        }
    }
}
