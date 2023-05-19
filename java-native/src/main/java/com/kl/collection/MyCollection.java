package com.kl.collection;

import java.util.ArrayList;
import java.util.List;

public class MyCollection {
    List<String> list = new ArrayList<>();

    public MyCollection() {
        list.add(0, "kleber");
        list.add(1, "Love");
        list.add("John");
        list.add("Jack");
        list.add(4, "Will");

        System.out.println(list.toString());

        String name = list.get(2);
        System.out.println(name);
        list.remove(name);

        System.out.println(list.toString());
    }
}
