package com.kl;

import java.util.Random;
import java.util.Stack;

public class MyStack {
    public static void main(String[] args) {
        Stack<Object> myStack = new Stack<>();

        Random random = new Random();
        int i = 5;

        while (i != 0){
            System.out.println("Push: " + myStack.push(random.nextInt(30)));
            i--;
        }

        System.out.println(myStack.toString());

        while (!myStack.isEmpty()){
            System.out.println("Pop: " + myStack.pop());
            i--;
        }

        System.out.println(myStack.toString());
    }
}
