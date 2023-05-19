package com.kl.nomal_class;

import com.kl.abstract_class.AbstractClass;

public class ClassToExtendAbstractClass extends AbstractClass {
    public ClassToExtendAbstractClass(){

    }
    @Override
    public void abstractMethod1() {
        System.out.println("abstractMethod1()");
    }

    @Override
    public void abstractMethod2() {
        System.out.println("abstractMethod2()");
    }
}
