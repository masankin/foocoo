package com.nomadic.foocoo.dispatcher;

import com.nomadic.foocoo.a.ATagFactory;
import com.nomadic.foocoo.css.CssTagFactory;
import com.nomadic.foocoo.html.HtmlContent;
import com.nomadic.foocoo.img.ImgTagFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by peaches on 2015-08-22.
 */
public class Dispatcher {

    public static String startUrl = "http://www.henha.com/";
    static int index = 0;
    private static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.err.println(t);
                    System.err.println(e);
                    e.printStackTrace();
                }
            });
            return thread;
        }
    };
    private static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(10, threadFactory);

    public static void main(String[] args) {
        //启动项目
        HtmlContent.getHtmlContent(startUrl);
        threadPoolExecutor.execute(new CssTagFactory());
        threadPoolExecutor.execute(new ATagFactory());
        threadPoolExecutor.execute(new ImgTagFactory());
    }
}
