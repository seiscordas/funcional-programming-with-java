package com.kl.collection;

import java.util.HashMap;
import java.util.Map;

public class MyHashMap {
    Map<Integer, String> myHashMap = new HashMap<Integer, String>();
    public MyHashMap() {
        myHashMap.put(0, "kleber");
        myHashMap.put(1, "Love");
        myHashMap.put(2, "John");
        myHashMap.put(3, "Jack");
        myHashMap.put(4, "Will");

        System.out.println(myHashMap.toString());

        String name = myHashMap.get(2);
        System.out.println(name);
        myHashMap.remove(2);

        System.out.println(myHashMap.toString());
    }
}
