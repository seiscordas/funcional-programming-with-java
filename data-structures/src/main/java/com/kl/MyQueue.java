package com.kl;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue {
    public static void main(String[] args) {
        Queue<Object> myQueue = new LinkedList<>();

        System.out.println("Push: " + myQueue.add("a"));
        System.out.println("Push: " + myQueue.add("b"));
        System.out.println("Push: " + myQueue.add("c"));
        System.out.println("Element: " + myQueue.element());
        System.out.println("Poll: " + myQueue.poll());
        System.out.println("Element: " + myQueue.element());

        myQueue.forEach(System.out::println);
    }
}
