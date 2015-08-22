package com.nomadic.foocoo.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by peaches on 2015-08-22.
 */
public class CollectionUtils {
    /**
     * 集合回调类，用于对集合中的每一个元素处理
     *
     * @param collection
     * @param nextCallback
     * @param <T>
     */
    public static <T> void collectionCallback(Collection<T> collection, NextCallback nextCallback) {
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            nextCallback.callback(next);
        }
    }

    /**
     * 集合回调类，用于对集合中的每一个元素处理
     *
     * @param collection
     * @param nextCallback
     * @param nextFilter
     * @param <T>
     */
    public static <T> void collectionCallback(Collection<T> collection, NextCallback nextCallback, NextFilter nextFilter) {
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (nextFilter.filter(next)) {
                nextCallback.callback(next);
            }
        }
    }

    public interface NextFilter<T> {
        boolean filter(T t);
    }

    public interface NextCallback<T> {
        void callback(T t);
    }
}
