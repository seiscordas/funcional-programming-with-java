package com.kl;

import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

public class MyTreeSet {
    public static void main(String[] args) {
        TreeSet<String> myTreeSet = new TreeSet();

        myTreeSet.add("kleber");
        myTreeSet.add("Jack");
        myTreeSet.add("John");
        myTreeSet.add("Richard");
        myTreeSet.add("Natan");

        System.out.println(myTreeSet);

        for (String s : myTreeSet) {
            System.out.println(s);
        }
    }
}
