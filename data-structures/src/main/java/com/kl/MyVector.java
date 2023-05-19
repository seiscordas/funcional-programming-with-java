package com.kl;

import java.util.Vector;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class MyVector {
    public static void main(String[] args) {
        Vector<Object> vector = new Vector<>();
        vector.addElement("Kleber Mart");
        vector.addElement(41);
        vector.addElement(67.50D);

        System.out.println(vector.toString());
        //while (vector.elements().hasMoreElements()){
        //    System.out.println("vector.elements().nextElement(): " + vector.elements().nextElement());
        //}
        while (vector.iterator().hasNext()){
            System.out.println("vector.iterator().next(): " + vector.iterator().next());
        }
    }
}