package com.kl;

import java.util.Comparator;

public class MyComparator implements Comparator<MyComparator>, Comparable<MyComparator> {
    private String name;
    private Integer age;

    public MyComparator() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MyComparator(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compare(MyComparator mc1, MyComparator mc2) {
        return mc1.age - mc2.age;
    }

    @Override
    public int compareTo(MyComparator mc) {
        return this.name.compareTo(mc.name);
    }
}
