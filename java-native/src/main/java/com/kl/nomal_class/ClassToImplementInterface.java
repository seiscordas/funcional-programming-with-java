package com.kl.nomal_class;

import com.kl.interfaces.IAction;

public class ClassToImplementInterface implements IAction {
    @Override
    public void action1(){
        System.out.println("action 1");
    }
    @Override
    public void action2(){
        System.out.println("action 2");
    }

    @Override
    public void action3() {
        System.out.println("action 3");
    }

    @Override
    public void action4() {
        System.out.println("action 4");
    }
}
