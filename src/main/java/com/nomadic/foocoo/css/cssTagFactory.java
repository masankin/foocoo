package com.nomadic.foocoo.css;

import com.nomadic.foocoo.core.UrlQueueFactory;
import com.nomadic.foocoo.html.HtmlContent;

/**
 * html css 标签工厂，
 * Created by peaches on 2015-08-22.
 */

public class CssTagFactory implements Runnable {
    public static int priority = 1;

    private static UrlQueueFactory urlQueueFactory = new UrlQueueFactory();

    public static UrlQueueFactory getUrlQueueFactory() {
        return urlQueueFactory;
    }

    @Override
    public void run() {
        while (true) {
            String url = getUrlQueueFactory().getNextUrl();
            if(url==null){
                try {
                    Thread.sleep(3000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("处理 css 标签url:" + url);
            HtmlContent.getTextContent(url);
            getUrlQueueFactory().handlerComplete(url);
            System.out.println(" css 标签处理完成url:" + url);
        }
    }
}
