package com.kl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MySyncList {
    public static void main(String[] args) {
        //List<String> list = Collections.synchronizedList(new ArrayList<>());

        List<String> list = new CopyOnWriteArrayList<>();

        list.add(0, "kleber");
        list.add(1, "Love");
        list.add("John");
        list.add("Jack");
        list.add(4, "Will");

        //synchronized (list) {
        //    list.forEach((Consumer<? super String>) System.out::println);
        //}
    }
}
