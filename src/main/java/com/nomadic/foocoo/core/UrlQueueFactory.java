package com.nomadic.foocoo.core;

import com.nomadic.foocoo.util.CollectionUtils;

import java.util.*;

/**
 * Created by peaches on 2015-08-22.
 */
public class UrlQueueFactory {

    //队列集合，去重
    public Set<String> allUrls = new HashSet<>();
    //等待抓取的队列
    public Queue<String> waitingQueue = new LinkedList<>();
    //已经抓取的队列
    public Queue<String> visitedQueue = new LinkedList<>();

    /**
     * 添加到等待抓取队列
     *
     * @param url
     * @return
     */
    public boolean addUrlToWaitingQueue(String url) {
        if (allUrls.add(url)) {
            waitingQueue.offer(url);
            return true;
        }
        return false;
    }

    /**
     * 批量添加url到等待抓取队列
     *
     * @param urls
     */
    public void addUrlToWaitingQueue(Collection<String> urls) {
        CollectionUtils.collectionCallback(urls, new CollectionUtils.NextCallback() {
            @Override
            public void callback(Object o) {
                waitingQueue.offer((String) o);
            }
        }, new CollectionUtils.NextFilter() {
            @Override
            public boolean filter(Object o) {
                return allUrls.add((String) o);
            }
        });
        //TODO 添加异步持久化
    }

    /**
     * 获取下一个url
     *
     * @return
     */
    public String getNextUrl() {
        return waitingQueue.peek();
    }

    /**
     * 已经处理过的url，从等待队列转移到已经抓取队列
     * 此设计目前只支持单线程
     *
     * @param url
     * @return
     */
    public boolean handlerComplete(String url) {
        if (url.equals(waitingQueue.peek()) || true) {
            visitedQueue.offer(waitingQueue.poll());
            return true;
        }
        return false;
    }
}
