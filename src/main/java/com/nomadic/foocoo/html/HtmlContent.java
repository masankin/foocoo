package com.nomadic.foocoo.html;

import com.nomadic.foocoo.a.ATagFactory;
import com.nomadic.foocoo.css.CssTagFactory;
import com.nomadic.foocoo.dispatcher.Dispatcher;
import com.nomadic.foocoo.img.ImgTagFactory;
import com.nomadic.foocoo.util.CollectionUtils;
import com.nomadic.foocoo.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by peaches on 2015-08-22.
 */
public class HtmlContent {
    public static String folderPath = "E:/foocoo/";

    public static void getHtmlContent(final String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(30000)
                    .get();

            String title = doc.title();
            FileUtils.saveDocToFileSystem(doc, folderPath, url);

            Elements aElements = doc.getElementsByTag("a");
            Elements imgElements = doc.getElementsByTag("img");
            Elements cssElements = doc.getElementsByTag("link");
            final List<String> aList = new LinkedList<>();
            final List<String> imgList = new LinkedList<>();
            final List<String> cssList = new LinkedList<>();
            CollectionUtils.collectionCallback(aElements, new CollectionUtils.NextCallback() {
                @Override
                public void callback(Object o) {
                    aList.add(((Element) o).absUrl("href"));
                }
            }, new CollectionUtils.NextFilter() {
                @Override
                public boolean filter(Object o) {
                    boolean result;
                    if (((Element) o).absUrl("href").startsWith(Dispatcher.startUrl)) {
                        result = true;
                    } else {
                        result = false;
                    }
                    return result;
                }
            });

            CollectionUtils.collectionCallback(imgElements, new CollectionUtils.NextCallback() {
                @Override
                public void callback(Object o) {
                    imgList.add(((Element) o).absUrl("src"));
                }
            }, new CollectionUtils.NextFilter() {
                @Override
                public boolean filter(Object o) {
                    String imgUrl = ((Element) o).absUrl("src");
                    boolean result = true;
                    try {
                        if (FileUtils.getFile(folderPath, imgUrl).exists()) {
                            result = false;
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    sout(result, imgUrl);
                    return result;
                }
            });

            CollectionUtils.collectionCallback(cssElements, new CollectionUtils.NextCallback() {
                @Override
                public void callback(Object o) {
                    cssList.add(((Element) o).absUrl("href"));
                }
            });
            ATagFactory.getUrlQueueFactory().addUrlToWaitingQueue(aList);
            ImgTagFactory.getUrlQueueFactory().addUrlToWaitingQueue(imgList);
            CssTagFactory.getUrlQueueFactory().addUrlToWaitingQueue(cssList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getTextContent(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            FileUtils.saveDocToFileSystem(doc, folderPath, url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int existsNum = 0;
    static int newNum = 0;

    public static void sout(boolean e, String url) {
        if (e) {
            System.out.println(newNum++ + " 新文件 " + url);
        } else {
            System.out.println(existsNum++ + " 文件 " + url + " 已经存在");
        }
    }
}
