package com.kl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyCompSort {
    public static void main(String[] args) {
        List<MyComparator> myComparators = new ArrayList<>();

        myComparators.add(new MyComparator("kleber", 41));
        myComparators.add(new MyComparator("Jack", 25));
        myComparators.add(new MyComparator("John", 30));
        myComparators.add(new MyComparator("Richard", 30));
        myComparators.add(new MyComparator("Natan", 30));

        for (MyComparator item : myComparators) {
            System.out.println(item.getName() + " | " + item.getAge());
        }

        System.out.println("#########################################");

        Collections.sort((List) myComparators);

        for (MyComparator item : myComparators) {
            System.out.println(item.getName() + " | " + item.getAge());
        }
    }
}
