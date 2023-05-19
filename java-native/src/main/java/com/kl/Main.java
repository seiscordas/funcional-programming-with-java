package com.kl;

import com.kl.annotation.MyAnnotation;
import com.kl.collection.MyCollection;
import com.kl.collection.MyHashMap;
import com.kl.io.MyFileHandler;
import com.kl.nomal_class.ClassToExtendAbstractClass;
import com.kl.nomal_class.ClassToImplementInterface;
import com.kl.nomal_class.ClassToUseMyAnnotation;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------ClassToImplementInterface-------");
        ClassToImplementInterface classToImplementInterface = new ClassToImplementInterface();

        classToImplementInterface.action1();
        classToImplementInterface.action2();
        classToImplementInterface.action3();
        classToImplementInterface.action4();

        ClassToExtendAbstractClass classToExtendAbstractClass = new ClassToExtendAbstractClass();
        
        classToExtendAbstractClass.abstractMethod1();
        classToExtendAbstractClass.abstractMethod2();
        classToExtendAbstractClass.nonAbstractMethod();
        System.out.println("###################################################");

        System.out.println("-------ClassToUseMyAnnotation-------");
        Class<ClassToUseMyAnnotation> classToUseMyAnnotation = ClassToUseMyAnnotation.class;

        if(classToUseMyAnnotation.isAnnotationPresent(MyAnnotation.class)){
            MyAnnotation annotation = classToUseMyAnnotation.getAnnotation(MyAnnotation.class);
            System.out.println("this annotation was created by: " + annotation.createdBy());
        }
        System.out.println("###################################################");

        System.out.println("-------MyFileHandler-------");
        MyFileHandler myFileHandler = new MyFileHandler();
        System.out.println("###################################################");

        System.out.println("-------Collection-------");
        MyCollection myCollection = new MyCollection();
        System.out.println("###################################################");

        System.out.println("-------MyHashMap-------");
        MyHashMap myHashMap = new MyHashMap();
        System.out.println("###################################################");


    }
}